package jason.app.brainstorm.product.service;

import jason.app.brainstorm.product.model.response.CatalogResponse;
import jason.app.brainstorm.product.model.response.ProductResponse;

public interface CatalogService {
	public CatalogResponse getCatalog();
	public ProductResponse getNews();
	public ProductResponse getBestSeller();
	public ProductResponse getPromotion();	
	
}
