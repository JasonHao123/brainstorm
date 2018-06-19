package jason.app.brainstorm.camel.gateway.service;

import javax.servlet.http.HttpServletRequest;

import jason.app.brainstorm.camel.gateway.model.Application;

public interface ApplicationIdentificationService {

	Application identifyApp(HttpServletRequest request);

}
