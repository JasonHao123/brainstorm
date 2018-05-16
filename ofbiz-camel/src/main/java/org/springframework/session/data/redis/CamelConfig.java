package org.springframework.session.data.redis;

import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.zipkin.ZipkinTracer;
import org.apache.ofbiz.base.container.ContainerException;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
@Import(RedisHttpSessionConfiguration.class)
@EnableRedisHttpSession
public class CamelConfig extends CamelConfiguration {
	@Bean
	public ZipkinTracer zipkinTracer() throws Exception {
		ZipkinTracer zipkin = new ZipkinTracer();
		// Configure a reporter, which controls how often spans are sent
		//   (the dependency is io.zipkin.reporter2:zipkin-sender-okhttp3)
		OkHttpSender sender = OkHttpSender.create("http://192.168.31.248:9411/api/v2/spans");
		zipkin.setSpanReporter(AsyncReporter.create(sender));
		// and then add zipkin to the CamelContext
		zipkin.init(camelContext());
		return zipkin;
	}
	
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory("localhost", 6379);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler(); // single threaded by default
	}


	@Bean
	public LocalDispatcher dispatcher() throws ContainerException {
		Delegator delegator = DelegatorFactory.getDelegator("default");
		return ServiceContainer.getLocalDispatcher("camel-dispatcher", delegator);
		
	}
}
