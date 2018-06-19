package jason.app.brainstorm.camel.gateway.service.impl;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import groovy.lang.GroovyShell;
import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService{


	public void decide(Authentication authentication, Application app, String url) {
		try {
			Integer result = (Integer) new GroovyShell().parse( "def evalute(def authentication, def app,def url) {\n" + 
					" println \"Hello, world!\"\n" + 
					" println authentication; \n" +
					" println url; \n" +
					" println app; \n" +					
					" return 0; \n" + 
					"}").invokeMethod( "evalute", new Object[] {authentication,app,url} ) ;
			System.out.println(result);
		} catch (CompilationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
