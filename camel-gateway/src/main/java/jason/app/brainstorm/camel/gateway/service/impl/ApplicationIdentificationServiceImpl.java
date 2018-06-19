package jason.app.brainstorm.camel.gateway.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.service.ApplicationIdentificationService;

@Service
public class ApplicationIdentificationServiceImpl implements ApplicationIdentificationService {

	@Override
	public Application identifyApp(HttpServletRequest request) {
		Application app = new Application();
		app.setCountry("CN");
		app.setName("SCM");
		app.setVersion("1.0");
		return app;
	}

}
