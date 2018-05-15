package org.springframework.session.data.redis;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.ofbiz.base.container.ContainerException;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@Configuration
@Import(RedisHttpSessionConfiguration.class)
@ComponentScan("org.springframework.session.data.redis")
@EnableRedisHttpSession
public class CamelConfig extends CamelConfiguration {
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory("localhost", 6379);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(); // single threaded by default
	}

	@Override
	protected CamelContext createCamelContext() throws Exception {
		LocalDispatcher dispatcher = createDispatcher();
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("dispatcher", dispatcher);
		return new DefaultCamelContext(registry);
	}

	private LocalDispatcher createDispatcher() throws ContainerException {
		Delegator delegator = DelegatorFactory.getDelegator("default");
		return ServiceContainer.getLocalDispatcher("camel-dispatcher", delegator);
		// return dispatcherFactory.createLocalDispatcher("camel-dispatcher",
		// delegator);
	}
}
