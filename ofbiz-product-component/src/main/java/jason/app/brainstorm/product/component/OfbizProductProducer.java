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
package jason.app.brainstorm.product.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jason.app.brainstorm.product.service.CatalogService;

/**
 * The OfbizProduct producer.
 */
public class OfbizProductProducer extends DefaultProducer {
    private static final transient Logger LOG = LoggerFactory.getLogger(OfbizProductProducer.class);
    private OfbizProductEndpoint endpoint;
	private String remaining;

    public OfbizProductProducer(OfbizProductEndpoint endpoint, String remaining) {
        super(endpoint);
        this.endpoint = endpoint;
        this.remaining = remaining;
    }

    public void process(Exchange exchange) throws Exception {
        System.out.println(exchange.getIn().getBody());   
        CatalogService service = (CatalogService) endpoint.getCamelContext().getRegistry().lookupByNameAndType("catalogService", CatalogService.class);
        if(service==null) {
        		exchange.getIn().setBody("service not found "+remaining);
        }else {
        		switch(remaining) {
	        		case "getCatalog":
	        			exchange.getIn().setBody(service.getCatalog());
	        			break;
	        		case "getCategory":
	        			String id = (String) exchange.getIn().getHeader("id");
	        			exchange.getIn().setBody(service.getCategory(id));
	        			break;
	        		case "getCategoryProducts":
	        			String id1 = (String) exchange.getIn().getHeader("id");
	        			exchange.getIn().setBody(service.getCategoryProducts(id1));
	        			break;
	        		case "getNews":
	        			exchange.getIn().setBody(service.getNews());
	        			break;
	        		case "getBestSeller":
	        			exchange.getIn().setBody(service.getBestSeller());
	        			break;
	        		case "getPromotion":
	        			exchange.getIn().setBody(service.getPromotion());
	        			break;
        		}
        }
    }

}
