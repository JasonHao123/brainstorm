package org.apache.ofbiz.security.controller;

import java.util.Collection;

import org.apache.ofbiz.security.model.User;
import org.apache.ofbiz.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value="/users")
	public @ResponseBody Collection<User> getUsers() {
		return securityService.findUsers();
	}
}
