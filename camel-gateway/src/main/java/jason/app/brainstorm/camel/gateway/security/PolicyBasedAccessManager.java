package jason.app.brainstorm.camel.gateway.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.service.ApplicationIdentificationService;
import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Component("accessDecisionManager")
public class PolicyBasedAccessManager implements AccessDecisionManager {
	
//	@Value("security.app.header")
//	private String appHeader;

	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private ApplicationIdentificationService identificationService;
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		FilterInvocation invoke = (FilterInvocation) object;
		HttpServletRequest request = invoke.getHttpRequest();
		Application app = identificationService.identifyApp(request);
		if(app==null) throw new AccessDeniedException("Invalid request!");
		policyService.decide(authentication,app,invoke.getRequestUrl());
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
