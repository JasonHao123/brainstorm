package jason.app.brainstorm.camel.gateway.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jason.app.brainstorm.camel.gateway.model.Application;

public class PolicyServiceImplTest {

	@Test
	public void testDecide() {
		PolicyServiceImpl service = new PolicyServiceImpl();
		ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		Application app = new Application();
		app.setCountry("CN");
		app.setName("SCM");
		app.setVersion("1.0");
		long start = new Date().getTime();
		for(int i=0;i<1000;i++) {
			service.decide(new AnonymousAuthenticationToken("GUEST", "GUEST", authorities),null,app, "/users");
		}
		long end = new Date().getTime();
		assertTrue(end-start<200);
	}

}
