package jason.app.brainstorm.product.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
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
             
             .to("product://getCatalog")
        // .route().to("bean:catalogService?method=getCatalog").removeHeaders("X-").endRest()
         .get("/catalog/{id}").description("Find user by ID")
         .outType(CatalogResponse.class)
         .param().name("id").type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
         .to("product://getCategory")
       //  .route().to("bean:catalogService?method=getCategory(${header.id})").removeHeaders("X-").endRest()
         
         .get("/category/{id}").description("Find user by ID")
         .outType(CatalogResponse.class)
         .param().name("id").type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
         .to("product://getCategoryProducts")
        // .route().to("bean:catalogService?method=getCategoryProducts(${header.id})").removeHeaders("X-").endRest()
         
         .get("/news").description("Find user by ID")
         .outType(CatalogResponse.class)
         .to("product://getNews")
	  //   .route().to("bean:catalogService?method=getNews").removeHeaders("X-").endRest()
         
	     .get("/bestseller").description("Find user by ID")
	     .outType(CatalogResponse.class)
	     .to("product://getBestSeller")
	   //  .route().to("bean:catalogService?method=getBestSeller").removeHeaders("X-").endRest()
	     
		 .get("/promotion").description("Find user by ID")
		 .outType(CatalogResponse.class)
		 .to("product://getPromotion");
		//.route().to("bean:catalogService?method=getPromotion").removeHeaders("X-").endRest();
		 
		
	}
}