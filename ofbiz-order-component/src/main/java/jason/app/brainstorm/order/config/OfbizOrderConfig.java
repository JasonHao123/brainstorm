package jason.app.brainstorm.order.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jason.app.brainstorm.order.service.ShoppingCartService;
import jason.app.brainstorm.order.service.impl.ShoppingCartServiceImpl;

@Configuration
@EntityScan(basePackages="jason.app.brainstorm.order.entity")
@EnableJpaRepositories(basePackages="jason.app.brainstorm.order.repository")
public class OfbizOrderConfig {
	
	@Bean
	public ShoppingCartService shoppingCartService() {
		return new ShoppingCartServiceImpl();
	}
	
}
