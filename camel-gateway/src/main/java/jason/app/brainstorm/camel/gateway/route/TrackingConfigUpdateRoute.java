package jason.app.brainstorm.camel.gateway.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import jason.app.brainstorm.camel.gateway.model.AppsConfig;
import jason.app.brainstorm.camel.gateway.service.ApplicationIdentificationService;
import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Component
public class TrackingConfigUpdateRoute extends RouteBuilder {

	@Autowired
	private ApplicationIdentificationService appService;
	
	@Autowired
	private PolicyService policyService;
	
    @Override
    public void configure() throws Exception {
        JaxbDataFormat jaxb = new JaxbDataFormat(AppsConfig.class.getPackage().getName());
        
    		from("file:/tmp/data").convertBodyTo(String.class).unmarshal(jaxb).bean(appService,"loadConfig");
    		from("file:/tmp/data2").convertBodyTo(String.class).unmarshal(jaxb).bean(policyService,"loadConfig");
    }

}
