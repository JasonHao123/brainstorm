package jason.app.brainstorm.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.product.constant.CatalogCategoryTypes;
import jason.app.brainstorm.product.constant.CategoryContentTypes;
import jason.app.brainstorm.product.constant.ProductPricePurposeTypes;
import jason.app.brainstorm.product.constant.ProductPriceTypes;
import jason.app.brainstorm.product.constant.ProductTypes;
import jason.app.brainstorm.product.entity.CatalogCategory;
import jason.app.brainstorm.product.entity.Category;
import jason.app.brainstorm.product.entity.CategoryContent;
import jason.app.brainstorm.product.entity.CategoryRollup;
import jason.app.brainstorm.product.entity.ProductCategory;
import jason.app.brainstorm.product.entity.ProductPrice;
import jason.app.brainstorm.product.model.response.ProductResponse;
import jason.app.brainstorm.product.model.vo.Product;
import jason.app.brainstorm.product.repository.CatalogCategoryRepository;
import jason.app.brainstorm.product.repository.CategoryContentRepository;
import jason.app.brainstorm.product.repository.CategoryRollupRepository;
import jason.app.brainstorm.product.repository.ElectronicTextRepository;
import jason.app.brainstorm.product.repository.ProductCategoryRepository;
import jason.app.brainstorm.product.repository.ProductPriceRepository;
import jason.app.brainstorm.product.service.CatalogService;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

	private static final int PAGE_SIZE = 10;

	@Autowired
	private CatalogCategoryRepository catalogRepo;
	
	@Autowired
	private CategoryRollupRepository categoryRepo;
	
	@Autowired
	private CategoryContentRepository cataContentRepo;
	
	@Autowired
	private ProductCategoryRepository productRepo;
	
	@Autowired
	private ElectronicTextRepository textRepo;
	
	@Autowired
	private ProductPriceRepository priceRepo;
	
	@Override
	public List<jason.app.brainstorm.product.model.vo.Category> getCatalog() {
		List<jason.app.brainstorm.product.model.vo.Category> response = new ArrayList<jason.app.brainstorm.product.model.vo.Category>();
		CatalogCategory root = catalogRepo.findFirstByCatalogIdAndType("WEBSTORE_CATALOG",CatalogCategoryTypes.PCCT_BROWSE_ROOT);
		if(root!=null) {
			List<CategoryRollup> list = categoryRepo.findByParentCategory(root.getCategory());
			if(list!=null) {
				List<jason.app.brainstorm.product.model.vo.Category> categories = new ArrayList<jason.app.brainstorm.product.model.vo.Category>();
				for(CategoryRollup category:list) {
					jason.app.brainstorm.product.model.vo.Category target = new jason.app.brainstorm.product.model.vo.Category();
					BeanUtils.copyProperties(category.getCategory(), target);
					if(target.getName()==null) {
						CategoryContent name = cataContentRepo.findFirstByCategoryIdAndType(target.getId(), CategoryContentTypes.CATEGORY_NAME);
						if(name!=null) {
							target.setName(textRepo.findOne(name.getContent().getResource().getId()).getText());
						}
					}
					categories.add(target);
				}
				response = categories;
			}
		}
		return response;
	}

	@Override
	public List<Product> getNews(String page) {
		
		return getProducts(CatalogCategoryTypes.PCCT_WHATS_NEW,page);
	}

	@Override
	public List<Product> getBestSeller(String page) {
		return getProducts(CatalogCategoryTypes.PCCT_BEST_SELL,page);
	}

	@Override
	public List<Product> getPromotion(String page) {
		return getProducts(CatalogCategoryTypes.PCCT_PROMOTIONS,page);
	}

	private List<Product> getProducts(String type,String page) {
		List<Product> response = new ArrayList<Product>();
		CatalogCategory root = catalogRepo.findFirstByCatalogIdAndType("WEBSTORE_CATALOG",type);
		if(root!=null) {
			List<Product> products = getProductsByCategory(root.getCategory(),page);
			response= products;
		}
		return response;
	}

	private List<Product> getProductsByCategory(String category,String page) {
		int pageNo = 0;
		if(page!=null && page.matches("[0-9]+")) {
			pageNo = Integer.parseInt(page)-1;
		}
		if(pageNo<0) {
			pageNo = 0;
		}
		List<CategoryRollup> list = categoryRepo.findByParentCategory(category);
		List<Category> categories = extractCategoryTree(list);
		Category rootCate = new Category();
		rootCate.setId(category);
		categories.add(rootCate);
		Sort sort = new Sort(new Sort.Order(Direction.ASC, "effectDate"));
		Pageable pageable = new PageRequest(pageNo, PAGE_SIZE, sort);
		List<ProductCategory> pc = productRepo.findByCategoryInAndProduct_TypeIn(categories, new String[] {ProductTypes.AGGREGATED,ProductTypes.DIGITAL_GOOD,ProductTypes.MARKETING_PKG,ProductTypes.FINISHED_GOOD,ProductTypes.SERVICE_PRODUCT},pageable);
		List<Product> products = new ArrayList<Product>();
		for(ProductCategory prod:pc) {
			Product product = new Product();
			BeanUtils.copyProperties(prod.getProduct(), product);
//			ProductContent content = prodContentRepo.findFirstByProductIdAndType(product.getId(),ProductContentTypes.IMAGE);
//			if(content!=null) {
//				product.setImage(content.getContent().getResource().getInfo());
//			}
			List<ProductPrice> prices = priceRepo.findByProductIdAndPurpose(product.getId(), ProductPricePurposeTypes.PURCHASE);
			setPrice(product,prices);
			products.add(product);
		}
		return products;
	}
	
	
	private void setPrice(Product product, List<ProductPrice> prices) {
		Double salesPrice = null;
		Double listPrice = null;
		for(ProductPrice price:prices) {
			switch(price.getType()) {
			case ProductPriceTypes.DEFAULT_PRICE:
				if(listPrice==null) {
					listPrice = price.getPrice();
				}
				break;
			case ProductPriceTypes.LIST_PRICE:
				listPrice = price.getPrice();
				break;
			case ProductPriceTypes.PROMO_PRICE:
				salesPrice = price.getPrice();
				break;
			}
		}
		if(salesPrice==null) {
			salesPrice = listPrice;
		}
		product.setListPrice(listPrice);
		product.setSalesPrice(salesPrice);
	}

	private List<Category> extractCategoryTree(List<CategoryRollup> list) {
		List<Category> result = new ArrayList<Category>();
		for(CategoryRollup category:list) {
			List<CategoryRollup> temp = categoryRepo.findByParentCategory(category.getCategory().getId());
			if(temp!=null && temp.size()>0) {
				result.addAll(extractCategoryTree(temp));
			}
			result.add(category.getCategory());
		}
		return result;
	}

	@Override
	public List<jason.app.brainstorm.product.model.vo.Category> getCategory(String id) {
		List<jason.app.brainstorm.product.model.vo.Category> response = new ArrayList<jason.app.brainstorm.product.model.vo.Category>();
		
		if(id!=null) {
			List<CategoryRollup> list = categoryRepo.findByParentCategory(id);
			if(list!=null) {
				List<jason.app.brainstorm.product.model.vo.Category> categories = new ArrayList<jason.app.brainstorm.product.model.vo.Category>();
				for(CategoryRollup category:list) {
					jason.app.brainstorm.product.model.vo.Category target = new jason.app.brainstorm.product.model.vo.Category();
					BeanUtils.copyProperties(category.getCategory(), target);
					if(target.getName()==null) {
						CategoryContent name = cataContentRepo.findFirstByCategoryIdAndType(target.getId(), CategoryContentTypes.CATEGORY_NAME);
						if(name!=null) {
							target.setName(textRepo.findOne(name.getContent().getResource().getId()).getText());
						}
					}
					categories.add(target);
				}
				response = categories;
			}
		}
		return response;
	}

	@Override
	public ProductResponse getCategoryProducts(String id,String page) {
		ProductResponse response = new ProductResponse();
		response.setProducts(getProductsByCategory(id,page));
		return response;
	}
}
