package jason.app.brainstorm.product.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jason.app.brainstorm.product.service.CatalogService;
import jason.app.brainstorm.product.service.impl.CatalogServiceImpl;

@Configuration
@EntityScan(basePackages="jason.app.brainstorm.product.entity")
@EnableJpaRepositories(basePackages="jason.app.brainstorm.product.repository")
public class OfbizProductConfig {
	
	@Bean
	public CatalogService catalogService() {
		return new CatalogServiceImpl();
	}
	
}
