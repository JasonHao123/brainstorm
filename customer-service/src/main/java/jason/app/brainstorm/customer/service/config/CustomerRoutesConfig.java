package jason.app.brainstorm.customer.service.config;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.spring.security.SpringSecurityAccessPolicy;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.camel.spi.AuthorizationPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;

import jason.app.brainstorm.customer.service.model.User;

@Configuration
public class CustomerRoutesConfig {
	
	@Bean
	public AuthorizationPolicy user(AuthenticationManager authenticationManager,AccessDecisionManager accessDecisionManager) {
		SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
		policy.setAuthenticationManager(authenticationManager);
		policy.setAccessDecisionManager(accessDecisionManager);
		policy.setId("user");
		policy.setSpringSecurityAccessPolicy(new SpringSecurityAccessPolicy("USER"));
		return policy;
	}

//	        restConfiguration().component("netty4-http").bindingMode(RestBindingMode.json)
//	            // and output using pretty print
//	            .dataFormatProperty("prettyPrint", "true")
//	            // setup context path and port number that netty will use
//	            .contextPath("/").port(8080)
//	            // add swagger api-doc out of the box
//	            .apiContextPath("/api-doc")
//	                .apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
//	                // and enable CORS
//	                .apiProperty("cors", "true");
//	
	
    @Bean
    public RouteBuilder routeBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
            	
                restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api-doc").host("192.168.2.1").port(8090).contextPath("/camel")
                    .apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0")
                    .apiProperty("cors", "true");
                
        		onException(CamelAuthorizationException.class).handled(true).transform().simple("Access Denied with the Policy of ${exception.policyId} !");

            
            rest("/users").description("User REST service")
                .consumes("application/json")
                .produces("application/json")

                .get().description("Find all users").outType(User[].class)
                    .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
                    .to("bean:userService?method=findUsers")
            
                .get("/{id}").description("Find user by ID")
                    .outType(User.class)
                    .param().name("id").type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
                    .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                    .to("bean:userService?method=findUser(${header.id})")

                .put("/{id}").description("Update a user").type(User.class)
                    .param().name("id").type(RestParamType.path).description("The ID of the user to update").dataType("integer").endParam()    
                    .param().name("body").type(RestParamType.body).description("The user to update").endParam()
                    .responseMessage().code(204).message("User successfully updated").endResponseMessage()
                    .to("bean:userService?method=updateUser");

            rest("/login").description("see user's product list")
            .consumes("application/json")
            .produces("application/json").post().description("see user's product list").to("direct:login");

            from("direct:login")
           // .policy("user")
            .to("bean:userService?method=login");
            
            rest("/logout").description("see user's product list")
            .consumes("application/json")
            .produces("application/json").post().description("see user's product list").to("direct:logout");

            from("direct:logout")
           // .policy("user")
            .to("bean:userService?method=logout");

            rest("/initLogin").description("see user's product list")
            .consumes("application/json")
            .produces("application/json").post().description("see user's product list").to("direct:initLogin");

            from("direct:initLogin")
           // .policy("user")
            .to("bean:userService?method=initLogin");
            
            	
                rest("/see").description("see user's product list")
                .consumes("application/json")
                .produces("application/json").post().description("see user's product list").to("direct:see");
                
                from("direct:see")
               // .policy("user")
                .to("bean:userService?method=test")
                .transform().simple("<html><body>Hi!, I'm jason on ${camelId}/${routeId} ${in.body}</body></html>");
                
            }
        };
    }
}