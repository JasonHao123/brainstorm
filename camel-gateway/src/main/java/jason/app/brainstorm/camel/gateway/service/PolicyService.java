package jason.app.brainstorm.camel.gateway.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import jason.app.brainstorm.camel.gateway.model.Application;

public interface PolicyService {

	void decide(Authentication authentication, HttpServletRequest request,Application app, String url);

}
