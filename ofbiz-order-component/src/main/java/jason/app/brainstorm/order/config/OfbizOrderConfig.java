package jason.app.brainstorm.order.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages="jason.app.brainstorm.order.entity")
@EnableJpaRepositories(basePackages="jason.app.brainstorm.order.repository")
public class OfbizOrderConfig {
	
//	@Bean
//	public CatalogService catalogService() {
//		return new CatalogServiceImpl();
//	}
	
}
