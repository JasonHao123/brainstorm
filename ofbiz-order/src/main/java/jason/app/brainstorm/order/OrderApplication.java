package jason.app.brainstorm.order;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.zipkin.starter.CamelZipkin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import jason.app.brainstorm.order.config.OfbizOrderConfig;
import jason.app.brainstorm.security.config.SecurityConfig;
import jason.app.brainstorm.security.config.WebMvcConfig;


@SpringBootApplication
@CamelZipkin
@Import({
	SecurityConfig.class,
	WebMvcConfig.class
	//OfbizOrderConfig.class
})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

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

