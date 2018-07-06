package jason.app.brainstorm.order.route;

import org.apache.camel.CamelAuthorizationException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.order.model.vo.ShoppingCart;
import jason.app.brainstorm.order.model.vo.ShoppingItem;

@Component
public class ShoppingCartRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true").apiContextPath("/api-doc").host("192.168.2.1").port(8090)
				.contextPath("/camel").apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0")
				.apiProperty("cors", "true");

		onException(CamelAuthorizationException.class).handled(true).transform()
				.simple("Access Denied with the Policy of ${exception.policyId} !");

		rest("/order").description("User REST service").consumes("application/json").produces("application/json")

				.post().description("Find all users").type(ShoppingCart.class).outType(ShoppingCart.class)
				.responseMessage().code(200).message("All users successfully returned").endResponseMessage()
				.to("order://placeOrder").

				post("/shoppingcart").description("Find all users").type(ShoppingCart.class).outType(ShoppingCart.class)
				.responseMessage().code(200).message("All users successfully returned").endResponseMessage()
				.to("order://calcShoppingcart");
	}
}