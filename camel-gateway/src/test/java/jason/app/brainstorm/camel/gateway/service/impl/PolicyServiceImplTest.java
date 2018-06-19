package jason.app.brainstorm.camel.gateway.service.impl;

import java.util.ArrayList;

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
		service.decide(new AnonymousAuthenticationToken("GUEST", "GUEST", authorities),app, "/users");
	}

}
