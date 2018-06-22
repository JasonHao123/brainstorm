package com.mkyong.helloworld.service;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.DelegatorFactory;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceContainer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider
implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) 
    throws AuthenticationException {

      String name = authentication.getName();
      String password = authentication.getCredentials().toString();
      try {
	      Delegator delegator = DelegatorFactory.getDelegator("default");
	      LocalDispatcher dispatcher = ServiceContainer.getLocalDispatcher("camel-dispatcher", delegator);
		  Map<String, Object> params = new HashMap<String, Object>();
		  params.put("login.username", name);
		  params.put("login.password", password);
		  
		  Map<String, Object>  result = dispatcher.runSync("userLogin", params);
		  System.out.println(result.get("responseMessage"));
		  System.out.println(result.get("userLogin"));
		  System.out.println(result.get("userLoginSession"));		
		  if("success".equals(result.get("responseMessage"))) {
		      ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		      roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		      UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(
		            name, password, roles);
		      token.setDetails(result.get("userLogin"));
		      return token;
		  }
      }catch(Exception e) {
    	  	e.printStackTrace();
      }
      return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
      return authentication.equals(
        UsernamePasswordAuthenticationToken.class);
  }
}