/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jason.app.brainstorm.network.operator.config;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManagerFactory;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.apache.camel.processor.idempotent.jpa.JpaMessageIdRepository;
import org.apache.camel.processor.idempotent.jpa.MessageProcessed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.SessionRepository;

import jason.app.brainstorm.network.operator.service.NetworkOperatorService;

@Configuration
@ConfigurationProperties("operator")
@EntityScan(basePackageClasses= {MessageProcessed.class}, basePackages= {"jason.app.brainstorm.network.operator.entity"})
public class NetworkOperatorRouteConfig {
	
	private String key;
	
	private String processorName;
	
	private String keyAlgorithm;
	
	private String cryptoAlgorithm;
	
	private String initializationVector;
	
	@Autowired
	private NetworkOperatorService networkOperatorService;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public JpaMessageIdRepository jpaStore() {
		JpaMessageIdRepository store = new JpaMessageIdRepository(entityManagerFactory,processorName);
		return store;
	}

    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // specific exception like login failure will be recorded by black list service
                onException(Exception.class).bean(networkOperatorService,"recordFailure");
//                errorHandler(deadLetterChannel("direct:dead"));
                
                SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), keyAlgorithm);
                CryptoDataFormat cryptoFormat = new CryptoDataFormat(cryptoAlgorithm, secretKey);
                cryptoFormat.setInitializationVector(initializationVector.getBytes("UTF-8"));
                
                rest("/api")
                    .post("/{serviceGroup}/{serviceId}")
                    .to("direct:service-call");

                // if the request matches black list then block the request
                from("direct:service-call")
	                .choice()
	                .when(networkOperatorService).stop()
	                .otherwise().to("seda:process")
	            .end();
                
	            
	            from("seda:process")
                    .removeHeaders("CamelHttp*")
//	                .unmarshal().base64()
//	                .unmarshal(cryptoFormat)
	                .bean(networkOperatorService,"detectIpChange")
                  //  .idempotentConsumer( jsonpath("header.app.requestId"),jpaStore())
                    .setProperty("system", jsonpath("header.app.system"))
                    .setProperty("country", jsonpath("header.app.country"))
                    .setProperty("version", jsonpath("header.app.version"))
                    .setHeader("X-CSRF-TOKEN",jsonpath("header.app.nounce"))
                    .setProperty("nonce",jsonpath("header.app.nounce"))
                    .setProperty("SESSION",jsonpath("header.app.session"))
                    .setHeader("SESSION",jsonpath("header.app.session"))
                    .setHeader("Cookie",constant("SESSION=").append(jsonpath("header.app.session")))
//                    .bean(networkOperatorService,"handleServiceUrl")
//                    .log("${header.serviceUrl}")
//                    .serviceCall("${header.serviceUrl}/say/${header.serviceId}")
                    .serviceCall("${header.serviceGroup}/say/${header.serviceId}")
                    .convertBodyTo(String.class)
                    .bean(networkOperatorService,"setNounce")
                    .removeHeaders("*")
//                    .marshal(cryptoFormat)
//                    .marshal().base64()
                    ;
                
                from("direct:dead")
                		.removeHeaders("X-*")
                		.log("service-call response : ${body}").setBody().simple("Exception happend!");
            }
        };
    }
    
    
	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	public void setKeyAlgorithm(String keyAlgorithm) {
		this.keyAlgorithm = keyAlgorithm;
	}

	public String getCryptoAlgorithm() {
		return cryptoAlgorithm;
	}

	public void setCryptoAlgorithm(String cryptoAlgorithm) {
		this.cryptoAlgorithm = cryptoAlgorithm;
	}

	public String getInitializationVector() {
		return initializationVector;
	}

	public void setInitializationVector(String initializationVector) {
		this.initializationVector = initializationVector;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}
}