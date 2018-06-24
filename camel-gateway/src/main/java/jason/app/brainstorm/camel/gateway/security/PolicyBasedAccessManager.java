package jason.app.brainstorm.camel.gateway.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.model.PolicyResult;
import jason.app.brainstorm.camel.gateway.service.ApplicationIdentificationService;
import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Component("accessDecisionManager")
public class PolicyBasedAccessManager implements AccessDecisionManager {
	private static Logger logger = LoggerFactory.getLogger(PolicyBasedAccessManager.class);

	@Autowired
	private PolicyService policyService;
	
	@Autowired
	private ApplicationIdentificationService identificationService;
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		logger.info("decide start");
		FilterInvocation invoke = (FilterInvocation) object;
		HttpServletRequest request = invoke.getHttpRequest();
		Application app = identificationService.identifyApp(request);
		if(app==null) throw new AccessDeniedException("Invalid request!");
		logger.info("detected app "+app);
		request.setAttribute("app", app);
		PolicyResult policy = policyService.decide(authentication,request,app,invoke.getRequestUrl());
		if(policy==null) throw new AccessDeniedException("Invalid request!");
		logger.info("deteced policy"+policy.getModule()+" "+policy.getUrl()+" "+policy.isHttps());
		request.setAttribute("policy", policy);
		logger.info("decide end");
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
