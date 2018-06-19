package jason.app.brainstorm.camel.gateway.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

@Component("accessDecisionManager")
public class CustomAccessManager implements AccessDecisionManager {
	
	@Value("security.app.header")
	private String header;

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation invoke = (FilterInvocation) object;
		String userAgent = invoke.getRequest().getHeader("User-Agent");
		System.out.println(userAgent);
		System.out.println(invoke.getRequestUrl());
		System.out.println(authentication);
		System.out.println(object);
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(FilterInvocation.class);
	}



}
