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
package org.springframework.session.data.redis;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRoute extends RouteBuilder {
	@Autowired
	private SomeBean someBean;

    @Override
    public void configure() throws Exception {
        // you can configure the route rule with Java DSL here
    		restConfiguration().contextPath("/").bindingMode(RestBindingMode.auto).host("0.0.0.0").port(8088);
//    	from("timer://foo?fixedRate=true&period=2000").to("ofbiz://getAllCategories?dispatcher=#dispatcher").transform().simple("Ping at ${date:now:yyyy-MM-dd HH:mm:ss}").to("log:hello").to("mock:end");

    	 rest("/say")
         .post("/bye").to("direct:bye");
				 from("direct:bye").to("ofbiz://getAllCategories?dispatcher=#dispatcher");
     		//from("direct:bye").to("ofbiz://getAllCategories?dispatcher=#dispatcher");
    }

}
