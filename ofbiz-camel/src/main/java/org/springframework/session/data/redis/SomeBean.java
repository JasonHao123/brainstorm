package org.springframework.session.data.redis;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository.RedisSession;
import org.springframework.stereotype.Component;

@Component
public class SomeBean {
	@Autowired
	private RedisOperationsSessionRepository sessionRepository;

	public void test(Exchange exchange) {
		for(String key:exchange.getProperties().keySet()) {
			System.out.println(key);
		}
		for(String key:exchange.getIn().getHeaders().keySet()) {
			System.out.println(key);
		}
		System.out.println(exchange.getIn().getClass());
		RedisSession session = sessionRepository.getSession((String) exchange.getIn().getHeader("SESSION"));
		if(session==null) {
			session = sessionRepository.createSession();
			System.out.println(session.getId());
			sessionRepository.save(session);
		}
	}
}
