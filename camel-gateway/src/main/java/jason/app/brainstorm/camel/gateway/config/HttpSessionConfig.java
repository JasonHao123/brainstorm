package jason.app.brainstorm.camel.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession
@Configuration
@ConfigurationProperties("redis")
public class HttpSessionConfig {
	private String host;

	private int port;

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

	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(host, port);
	}
	
	 @Bean
	 RedisTemplate< String, CsrfToken> redisTemplate() {
	  final RedisTemplate< String, CsrfToken > template =  new RedisTemplate< String, CsrfToken >();
	  template.setConnectionFactory( connectionFactory() );
	  template.setKeySerializer( new StringRedisSerializer() );
	  template.setHashKeySerializer( new StringRedisSerializer() );
	  template.setHashValueSerializer( new JdkSerializationRedisSerializer() );
	  return template;
	 }
}