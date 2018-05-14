package jason.app.ofbiz.camel.config;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("jason.app.ofbiz.camel")
public class CamelConfig extends CamelConfiguration{

}
