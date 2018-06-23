package jason.app.brainstorm.camel.gateway.config;

import org.apache.camel.component.spring.security.SpringSecurityAccessPolicy;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.spi.AuthorizationPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AccessDecisionManager accessDecisionManager;
	
	@Bean
	public AuthorizationPolicy user(AuthenticationManager authenticationManager,AccessDecisionManager accessDecisionManager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAuthenticationManager(authenticationManager);
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setId("user");
		policy.setSpringSecurityAccessPolicy(new SpringSecurityAccessPolicy("USER"));
		return policy;
	}
	
	@Bean
	public HttpSessionCsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
		return repo;
	}
	
	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().accessDecisionManager(accessDecisionManager);

	}
	// @formatter:on

	// @formatter:off
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}

	public AccessDecisionManager getAccessDecisionManager() {
		return accessDecisionManager;
	}

	public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
		this.accessDecisionManager = accessDecisionManager;
	}
	
	
}
