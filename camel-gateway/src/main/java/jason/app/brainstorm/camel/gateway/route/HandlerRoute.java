package jason.app.brainstorm.camel.gateway.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Component
public class HandlerRoute extends RouteBuilder {

	@Autowired
	private PolicyService policyService;

	@Override
	public void configure() throws Exception {
		 from("servlet://?matchOnUriPrefix=true").streamCaching().setProperty("method",simple("${header.CamelHttpMethod}"))
		 .removeHeaders("CamelHttp*")
		 .bean(policyService,"setHeaders")
		  .choice()
          .when(header("isHttps").isEqualTo(true))
     	 	.serviceCall("${header.module}","https://${header.module}/${header.service}?sslContextParameters=#ssltest")
          .otherwise()
          	.serviceCall("${header.module}/${header.service}");
		 
	
	}

}
