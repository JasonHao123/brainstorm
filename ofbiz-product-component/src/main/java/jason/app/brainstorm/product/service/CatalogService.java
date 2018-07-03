package jason.app.brainstorm.product.service;

import java.util.List;

import jason.app.brainstorm.product.model.response.CatalogResponse;
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
	
}
