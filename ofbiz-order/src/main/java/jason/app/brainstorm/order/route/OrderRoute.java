package jason.app.brainstorm.order.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.order.service.CustomOrderService;

@Component
public class OrderRoute extends RouteBuilder {

	@Autowired
	private CustomOrderService orderService;
	
	@Override
	public void configure() throws Exception {
		 rest("/wishlist").description("User REST service")
         .consumes("application/json")
         .produces("application/json")
     
         .post("/add").description("Find user by ID").to("seda:updateWishList")
		 .post("/remove").description("Find user by ID").to("seda:updateWishList")
		 .get("/list").description("Find user by ID").to("seda:listWishList")
		 .get("/clear").description("Find user by ID").to("seda:clearWishList");
		 
		 rest("/shoppingcart").description("User REST service")
         .consumes("application/json")
         .produces("application/json")
     
         .post("/add").description("Find user by ID").to("seda:updateCart")
		 .post("/remove").description("Find user by ID").to("seda:updateCart")
		 .get("/clear").description("Find user by ID").to("seda:clearCart")
		 .get("/list").description("Find user by ID").to("seda:updateCart");
		 
		 from("seda:updateCart").bean(orderService,"setCurrentUserHeader")
		 .choice()
		 	.when().jsonpath("$.request.product_id", true)
		 		.setHeader("product_id", jsonpath("$.request.product_id"))
		 		.setHeader("quantity", jsonpath("$.request.quantity"))
		 .endChoice()
		 .to("order://loadShoppingCart")
		 .bean(orderService,"modifyCart")
		 .choice()
		 	.when().header("$header.product_id}")
		 		.to("order://saveShoppingCart")
		 .endChoice()
		 .to("order://calcShoppingCart");
		 
		 from("seda:clearCart").bean(orderService,"setCurrentUserHeader").to("order://clearShoppingCart");
		 
		 from("seda:updateWishList").bean(orderService,"setCurrentUserHeader").choice().when().jsonpath("$.request.product_id", true).setHeader("product_id", jsonpath("$.request.product_id")).setHeader("quantity", jsonpath("$.request.quantity")).endChoice().to("order://updateWishList");
		 
		 from("seda:clearWishList").bean(orderService,"setCurrentUserHeader").to("order://clearWishList");
		 	 
 
	}
}