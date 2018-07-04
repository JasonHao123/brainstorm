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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
			String id = (String) exchange.getIn().getHeader("id");
			String pageNo = (String) exchange.getIn().getHeader("pageNo");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        		switch(remaining) {
	        		case "getCatalog":
	        			exchange.getIn().setBody(service.getCatalog());
	        			break;
	        		case "getCategory":
	        			
	        			exchange.getIn().setBody(service.getCategory(id));
	        			break;
	        		case "getCategoryProducts":
	        			exchange.getIn().setBody(service.getCategoryProducts(id,pageNo));
	        			break;
	        		case "getNews":
	        			exchange.getIn().setBody(service.getNews(pageNo));
	        			break;
	        		case "getBestSeller":
	        			exchange.getIn().setBody(service.getBestSeller(pageNo));
	        			break;
	        		case "getPromotion":
	        			exchange.getIn().setBody(service.getPromotion(pageNo));
	        			break;
	        		case "getWishList":
	        			exchange.getIn().setBody(service.getWishList());
	        			break;
	        		case "addWishListItem":
	        			exchange.getIn().setBody(service.addWishListItem(id));
	        			break;
	        		case "deleteWishListItem":
	        			exchange.getIn().setBody(service.deleteWishListItem(id));
	        			break;
        		}
        }
    }

}
