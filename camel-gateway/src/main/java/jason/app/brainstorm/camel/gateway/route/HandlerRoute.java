package jason.app.brainstorm.camel.gateway.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import jason.app.brainstorm.camel.gateway.model.AppsConfig;
import jason.app.brainstorm.camel.gateway.service.ApplicationIdentificationService;
import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Component
public class HandlerRoute extends RouteBuilder {

	@Autowired
	private ApplicationIdentificationService appService;
	
	@Autowired
	private PolicyService policyService;
	
    @Override
    public void configure() throws Exception {
		from("servlet://?matchOnUriPrefix=true").streamCaching().to("http4://wwww.baidu.com?bridgeEndpoint=true");
    }

}
