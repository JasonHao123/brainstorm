package jason.app.brainstorm.camel.gateway.service;

import org.springframework.security.core.Authentication;

import jason.app.brainstorm.camel.gateway.model.Application;

public interface PolicyService {

	void decide(Authentication authentication, Application app, String url);

}
