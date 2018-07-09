package jason.app.brainstorm.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import jason.app.brainstorm.security.service.impl.CustomUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = "jason.app.brainstorm.security.entity")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public UserDetailsService customUserDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public AffirmativeBased accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		voters.add(new RoleVoter());
		AffirmativeBased accessDecisionManager = new AffirmativeBased(voters);
		accessDecisionManager.setAllowIfAllAbstainDecisions(true);
		return accessDecisionManager;
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().anonymous().principal("guest").authorities("ROLE_GUEST")
		.and().authorizeRequests().anyRequest().permitAll().and().rememberMe().key("token").rememberMeServices(rememberMeServices()).tokenRepository(tokenRepository());
	}
	
	@Bean
	public PersistentTokenRepository tokenRepository() {
		// TODO Auto-generated method stub
		return new InMemoryTokenRepositoryImpl();
	}

	@Bean
	public RememberMeServices rememberMeServices() {
		// TODO Auto-generated method stub
		TokenBasedRememberMeServices rem = new TokenBasedRememberMeServices("token", customUserDetailsService());
		rem.setAlwaysRemember(true);
		return rem;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new ShaPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService()).passwordEncoder(passwordEncoder());
	}

}
