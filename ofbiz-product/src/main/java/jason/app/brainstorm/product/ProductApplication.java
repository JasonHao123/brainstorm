package jason.app.brainstorm.product;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import jason.app.brainstorm.product.config.OfbizProductConfig;
import jason.app.brainstorm.security.config.SecurityConfig;
import jason.app.brainstorm.security.config.WebMvcConfig;


@SpringBootApplication
@CamelZipkin
@Import({
	SecurityConfig.class,
	WebMvcConfig.class,
	OfbizProductConfig.class
})
//@ImportResource("classpath:camel-context.xml")
public class ProductApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
    
//    @Bean
//	public ZipkinTracer zipkinTracer(CamelContext context,@Value("${camel.zipkin.endpoint}") String host) throws Exception {
//		ZipkinTracer zipkin = new ZipkinTracer();
//		OkHttpSender sender = OkHttpSender.create(host);
//		zipkin.setSpanReporter(AsyncReporter.create(sender));
//		zipkin.init(context);
//		return zipkin;
//	}


    @Bean
    public ServletRegistrationBean camelServletRegistrationBean() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/*");
        registration.setName("CamelServlet");
        return registration;
    }

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(); // single threaded by default
	}
}

