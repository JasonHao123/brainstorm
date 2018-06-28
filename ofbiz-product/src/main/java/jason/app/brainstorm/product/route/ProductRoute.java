package jason.app.brainstorm.product.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import jason.app.brainstorm.product.model.response.CatalogResponse;

@Component
public class ProductRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		 rest("/product").description("User REST service")
         .consumes("application/json")
         .produces("application/json")
     
         .get("/catalog").description("Find user by ID")
             .outType(CatalogResponse.class)
             .responseMessage().code(200).message("Catalog successfully returned").endResponseMessage()
         .route().to("bean:catalogService?method=getCatalog").removeHeaders("X-").endRest()
         .get("/news").description("Find user by ID")
         .outType(CatalogResponse.class)
         .responseMessage().code(200).message("Catalog successfully returned").endResponseMessage()
	     .route().to("bean:catalogService?method=getNews").removeHeaders("X-").endRest()
	     .get("/bestseller").description("Find user by ID")
	     .outType(CatalogResponse.class)
	     .responseMessage().code(200).message("Catalog successfully returned").endResponseMessage()
	     .route().to("bean:catalogService?method=getBestSeller").removeHeaders("X-").endRest()
		 .get("/promotion").description("Find user by ID")
		 .outType(CatalogResponse.class)
		 .responseMessage().code(200).message("Catalog successfully returned").endResponseMessage()
		.route().to("bean:catalogService?method=getPromotion").removeHeaders("X-").endRest();
		 
		
	}
}