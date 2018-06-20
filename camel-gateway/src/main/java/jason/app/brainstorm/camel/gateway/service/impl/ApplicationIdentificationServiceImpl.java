package jason.app.brainstorm.camel.gateway.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.model.AppsConfig;
import jason.app.brainstorm.camel.gateway.service.ApplicationIdentificationService;

@Service
public class ApplicationIdentificationServiceImpl implements ApplicationIdentificationService {

	@Autowired
	private RedisTemplate<String, List<String>> redisTemplate;

	public void loadConfig(AppsConfig config) {
		for(String str:config.getApp()) {
			System.out.println(str);
		}
		redisTemplate.opsForValue().set("apps", config.getApp());
	}
	
	@Cacheable
	public List<Script> getIdentificationScripts() {
		List<String> app = redisTemplate.opsForValue().get("apps");
		ArrayList<Script> scripts = new ArrayList<Script>();
		if(app!=null) {
			for(String str:app) {
				scripts.add(new GroovyShell().parse(str));
			}
		}
		return scripts;
	}
	@Override
	public Application identifyApp(HttpServletRequest request) {
		Application app = null;
		for(Script script:getIdentificationScripts()) {
			app = (Application) script.invokeMethod( "evalute", new Object[] {request}) ;
			if(app!=null) break;
		}		
		return app;
	}

}
