package jason.app.brainstorm.party.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.party.model.User;
import jason.app.brainstorm.party.model.request.LoginRequest;

@Component
public class SecurityRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		 rest("/party").description("User REST service")
         .consumes("application/json")
         .produces("application/json")
     
         .post("/security/login").description("Find user by ID")
         	 .type(LoginRequest.class)
             .outType(User.class)
             .param().name("id").type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
             .responseMessage().code(200).message("User successfully returned").endResponseMessage().route()
             .to("bean:userService?method=login(${body})").removeHeaders("X-").endRest();
		
	}
}