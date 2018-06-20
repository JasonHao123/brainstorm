package jason.app.brainstorm.security.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jason.app.brainstorm.security.model.User;
import jason.app.brainstorm.security.service.SecurityService;

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
