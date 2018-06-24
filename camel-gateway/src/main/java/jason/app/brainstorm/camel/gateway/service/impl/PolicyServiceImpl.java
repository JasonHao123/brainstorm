package jason.app.brainstorm.camel.gateway.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.apache.camel.http.common.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import groovy.lang.GroovyShell;
import groovy.lang.Script;
import jason.app.brainstorm.camel.gateway.model.Application;
import jason.app.brainstorm.camel.gateway.model.PoliciesConfig;
import jason.app.brainstorm.camel.gateway.model.PolicyResult;
import jason.app.brainstorm.camel.gateway.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {
	@Autowired
	private RedisTemplate<String, List<String>> redisTemplate;
	
	private static final String POLICY_PREFIX = "policy:";

	public void loadConfig(@Header("CamelFileName") String key, PoliciesConfig config) {
		key = key.substring(0, key.length() - 4);
		key = POLICY_PREFIX+key;
		System.out.println(key);
		for (String str : config.getPolicy()) {
			System.out.println(str);
		}
		redisTemplate.opsForValue().set(key, config.getPolicy());
	}

	@Cacheable
	public List<Script> getPolicyScripts(String key) {
		List<String> app = redisTemplate.opsForValue().get(key);
		ArrayList<Script> scripts = new ArrayList<Script>();
		if(app!=null) {
			for (String str : app) {
				scripts.add(new GroovyShell().parse(str));
			}
		}
		return scripts;
	}

	public PolicyResult decide(Authentication authentication, HttpServletRequest request, Application app, String url) {
		for (Script script : getPolicyScripts(POLICY_PREFIX+app.getName() + "-" + app.getVersion() + "-" + app.getCountry())) {
			// null not support
			// OK: OK
			// not null: error message
			PolicyResult result = (PolicyResult) script.invokeMethod("evalute",
					new Object[] { authentication, request, app, url });
			switch (result.getStatus()) {
			case 0:
				return result;
			case 1:
				throw new AccessDeniedException(result.getErrorMessage());

			}
		}

		for (Script script : getPolicyScripts(POLICY_PREFIX+app.getName() + "-" + app.getVersion())) {
			// null not support
			// OK: OK
			// not null: error message
			PolicyResult result = (PolicyResult) script.invokeMethod("evalute",
					new Object[] { authentication, request, app, url });
			switch (result.getStatus()) {
			case 0:
				return result;
			case 1:
				throw new AccessDeniedException(result.getErrorMessage());

			}
		}
		
		for (Script script : getPolicyScripts(POLICY_PREFIX+app.getName())) {
			// null not support
			// OK: OK
			// not null: error message
			PolicyResult result = (PolicyResult) script.invokeMethod("evalute",
					new Object[] { authentication, request, app, url });
			switch (result.getStatus()) {
			case 0:
				return result;
			case 1:
				throw new AccessDeniedException(result.getErrorMessage());

			}
		}
		return null;
	}

	@Override
	public void setHeaders(Exchange exchange) {
		// TODO Auto-generated method stub
		if(exchange.getMessage() instanceof HttpMessage) {
			HttpMessage msg = (HttpMessage) exchange.getMessage();
			for(Entry<String,Object> entry:msg.getHeaders().entrySet()) {
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
			PolicyResult policy = (PolicyResult) msg.getRequest().getAttribute("policy");
			if(policy!=null) {
				exchange.getIn().setHeader("CamelHttpMethod", exchange.getProperty("method"));
				exchange.getIn().setHeader("module", policy.getModule());
				exchange.getIn().setHeader("service", policy.getUrl().substring(1));
				exchange.getIn().setHeader("isHttps", policy.isHttps());
			}else {
				throw new AccessDeniedException("Invalid request!");
			}
			
		}
	}

}
