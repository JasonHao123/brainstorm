package jason.app.brainstorm.camel.gateway.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

public interface Policy {
	public boolean support(Authentication auth,HttpServletRequest request);
	public void evaluate(Authentication auth,HttpServletRequest request) throws AccessDeniedException, InsufficientAuthenticationException;
}
