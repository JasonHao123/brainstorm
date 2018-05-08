package jason.app.brainstorm.network.operator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class NetworkOperatorConfig {
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory("192.168.2.1",6379); 
	}
	
	 @Bean
	 RedisTemplate< String, CsrfToken > redisTemplate() {
	  final RedisTemplate< String, CsrfToken > template =  new RedisTemplate< String, CsrfToken >();
	  template.setConnectionFactory( connectionFactory() );
	  template.setKeySerializer( new StringRedisSerializer() );
	  template.setHashKeySerializer( new StringRedisSerializer() );
	  template.setHashValueSerializer( new JdkSerializationRedisSerializer() );
	  return template;
	 }
	
}
