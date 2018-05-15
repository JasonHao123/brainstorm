package jason.app.brainstorm.network.operator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@ConfigurationProperties("redis")
public class NetworkOperatorConfig {
	private String host;
	
	private int port;
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(host,port); 
	}
	
	 @Bean
	 RedisTemplate< String, String > redisTemplate() {
	  final RedisTemplate< String, String > template =  new RedisTemplate< String, String >();
	  template.setConnectionFactory( connectionFactory() );
	  template.setKeySerializer( new StringRedisSerializer() );
	  template.setHashKeySerializer( new StringRedisSerializer() );
	  template.setHashValueSerializer( new StringRedisSerializer() );
	  return template;
	 }

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
