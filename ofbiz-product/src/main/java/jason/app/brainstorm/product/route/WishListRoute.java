package jason.app.brainstorm.product.route;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.product.model.vo.Product;

@Component
public class WishListRoute extends RouteBuilder {

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

    
    rest("/product/wishlist").description("User REST service")
        .consumes("application/json")
        .produces("application/json")

        .get().description("Find all users").outType(Product[].class)
            .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
            .to("product://getWishList")
    
        .delete("/{id}").description("Find user by ID")
            .outType(Product.class)
            .param().name("id").type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
            .responseMessage().code(200).message("User successfully returned").endResponseMessage()
            .to("product://deleteWishListItem")

        .post("/{id}").description("Update a user").outType(Product.class)
            .param().name("id").type(RestParamType.path).description("The ID of the user to update").dataType("integer").endParam()    
         //   .param().name("body").type(RestParamType.body).description("The user to update").endParam()
            .responseMessage().code(204).message("User successfully updated").endResponseMessage()
            .to("product://addWishListItem");
	}
}