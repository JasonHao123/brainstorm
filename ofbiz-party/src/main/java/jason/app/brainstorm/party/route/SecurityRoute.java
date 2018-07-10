package jason.app.brainstorm.party.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.party.model.request.LoginRequest;
import jason.app.brainstorm.party.model.request.RegisterRequest;
import jason.app.brainstorm.party.model.response.LoginResponse;
import jason.app.brainstorm.party.model.response.LogoutResponse;
import jason.app.brainstorm.party.model.response.RegisterResponse;

@Component
public class SecurityRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		 rest("/user").description("User REST service")
         .consumes("application/json")
         .produces("application/json")
     
         .post("/login").description("Find user by ID")
         	 .type(LoginRequest.class)
             .outType(LoginResponse.class)
             .param().name("id").type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
             .responseMessage().code(200).message("User successfully returned").endResponseMessage().route()
             .to("bean:userService?method=login").removeHeaders("X-").endRest()
             
         .get("/logout").description("Find user by ID")
             .outType(LogoutResponse.class)
             .responseMessage().code(200).message("User successfully returned").endResponseMessage().route()
             .to("bean:userService?method=logout").removeHeaders("X-").endRest()
     
         .post("/register").description("Find user by ID")
         	 .type(RegisterRequest.class)
             .outType(RegisterResponse.class)
             .param().name("username").type(RestParamType.body).description("The ID of the user").dataType("integer").endParam()
             .param().name("password").type(RestParamType.body).description("The ID of the user").dataType("integer").endParam()
             .param().name("password2").type(RestParamType.body).description("The ID of the user").dataType("integer").endParam()
             .responseMessage().code(200).message("User successfully returned").endResponseMessage().route()
             .to("bean:userService?method=register(${body})").removeHeaders("X-").endRest();
		
	}
}