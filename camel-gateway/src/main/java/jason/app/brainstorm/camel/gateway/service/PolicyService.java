package jason.app.brainstorm.camel.gateway.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.springframework.security.core.Authentication;

import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.model.PolicyResult;

public interface PolicyService {

	PolicyResult decide(Authentication authentication, HttpServletRequest request,Application app, String url);
	void setHeaders(Exchange exchange);
}
