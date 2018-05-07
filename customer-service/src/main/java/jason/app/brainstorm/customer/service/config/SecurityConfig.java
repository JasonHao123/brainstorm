/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jason.app.brainstorm.customer.service.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
@EnableRedisHttpSession
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory("192.168.11.251",6379); 
	}
	
	@Bean
	public HttpSessionCsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repo = new HttpSessionCsrfTokenRepository();
		return repo;
	}
	
	@Bean
	public AffirmativeBased accessDecisionManager() {
		List<AccessDecisionVoter<? extends Object>> voters = new ArrayList<AccessDecisionVoter<? extends Object>>();
		voters.add(new RoleVoter());
		AffirmativeBased accessDecisionManager = new AffirmativeBased(voters);
		accessDecisionManager.setAllowIfAllAbstainDecisions(true);
		return accessDecisionManager;
	}
	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
	        private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");
	        private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/v[0-9]*/.*", null);

	        @Override
	        public boolean matches(HttpServletRequest request) {
	            // No CSRF due to allowedMethod
	            if(allowedMethods.matcher(request.getMethod()).matches())
	                return false;

	            // No CSRF due to api call
	            if(request.getRequestURL().toString().endsWith("/camel/initLogin")) {
	            		return false;
	            }
	            // CSRF for everything else that is not an API call or an allowedMethod
	            return true;
	        }
	    }).and()
		.anonymous().principal("guest").authorities("ROLE_GUEST").and()//<anonymous username="guest" granted-authority="ROLE_GUEST" />
				.authorizeRequests()
					.antMatchers("/css/**", "/index","/login**","/camel/api-doc","/camel/initLogin","/camel/login").permitAll()
					.antMatchers("/user/**").hasRole("USER")
					.antMatchers("/camel/**").hasAnyRole("USER","ADMIN")//hasRole("USER")
					.and().formLogin()
				//.formLogin().loginPage("/login").failureUrl("/login-error")
					;
	}
	// @formatter:on

	// @formatter:off
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
	// @formatter:on
}
