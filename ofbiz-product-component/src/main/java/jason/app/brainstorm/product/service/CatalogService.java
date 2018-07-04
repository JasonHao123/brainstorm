package jason.app.brainstorm.product.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import jason.app.brainstorm.product.model.response.ProductResponse;
import jason.app.brainstorm.product.model.vo.Category;
import jason.app.brainstorm.product.model.vo.Product;

public interface CatalogService {
	public List<Category> getCatalog();
	public List<Category> getCategory(String id);
	public ProductResponse getCategoryProducts(String id,String page);
	
	public List<Product> getNews(String page);
	public List<Product> getBestSeller(String page);
	public List<Product> getPromotion(String page);
	
	@Secured("ROLE_END_USER_CUSTOMER")
	public List<Product> getWishList();
	@Secured("ROLE_END_USER_CUSTOMER")
	public Product addWishListItem(String id);
	@Secured("ROLE_END_USER_CUSTOMER")
	public Product deleteWishListItem(String id);	
	
}
