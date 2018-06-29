package jason.app.brainstorm.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.product.constant.CatalogCategoryTypes;
import jason.app.brainstorm.product.constant.CategoryContentTypes;
import jason.app.brainstorm.product.constant.ProductContentTypes;
import jason.app.brainstorm.product.constant.ProductTypes;
import jason.app.brainstorm.product.entity.CatalogCategory;
import jason.app.brainstorm.product.entity.Category;
import jason.app.brainstorm.product.entity.CategoryContent;
import jason.app.brainstorm.product.entity.CategoryRollup;
import jason.app.brainstorm.product.entity.ProductCategory;
import jason.app.brainstorm.product.entity.ProductContent;
import jason.app.brainstorm.product.model.response.CatalogResponse;
import jason.app.brainstorm.product.model.response.ProductResponse;
import jason.app.brainstorm.product.model.vo.Product;
import jason.app.brainstorm.product.repository.CatalogCategoryRepository;
import jason.app.brainstorm.product.repository.CategoryContentRepository;
import jason.app.brainstorm.product.repository.CategoryRollupRepository;
import jason.app.brainstorm.product.repository.ElectronicTextRepository;
import jason.app.brainstorm.product.repository.ProductCategoryRepository;
import jason.app.brainstorm.product.repository.ProductContentRepository;
import jason.app.brainstorm.product.service.CatalogService;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {

	@Autowired
	private CatalogCategoryRepository catalogRepo;
	
	@Autowired
	private CategoryRollupRepository categoryRepo;
	
	@Autowired
	private CategoryContentRepository cataContentRepo;
	
	@Autowired
	private ProductContentRepository prodContentRepo;
	
	@Autowired
	private ProductCategoryRepository productRepo;
	
	@Autowired
	private ElectronicTextRepository textRepo;
	
	@Override
	public CatalogResponse getCatalog() {
		CatalogResponse response = new CatalogResponse();
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
				response.setCategories(categories);
			}
		}else {
			response.setStatus(-1);
			response.setMessage("No category found");
		}
		return response;
	}

	@Override
	public ProductResponse getNews() {
		
		return getProducts(CatalogCategoryTypes.PCCT_WHATS_NEW);
	}

	@Override
	public ProductResponse getBestSeller() {
		return getProducts(CatalogCategoryTypes.PCCT_BEST_SELL);
	}

	@Override
	public ProductResponse getPromotion() {
		return getProducts(CatalogCategoryTypes.PCCT_PROMOTIONS);
	}

	private ProductResponse getProducts(String type) {
		ProductResponse response = new ProductResponse();
		CatalogCategory root = catalogRepo.findFirstByCatalogIdAndType("WEBSTORE_CATALOG",type);
		if(root!=null) {
			List<Product> products = getProductsByCategory(root.getCategory());
			response.setProducts(products);
		}else {
			response.setStatus(-1);
			response.setMessage("No category found");
		}
		return response;
	}

	private List<Product> getProductsByCategory(String category) {
		List<CategoryRollup> list = categoryRepo.findByParentCategory(category);
		List<Category> categories = extractCategoryTree(list);
		Category rootCate = new Category();
		rootCate.setId(category);
		categories.add(rootCate);
		List<ProductCategory> pc = productRepo.findByCategoryInAndProduct_TypeIn(categories, new String[] {ProductTypes.AGGREGATED,ProductTypes.DIGITAL_GOOD,ProductTypes.MARKETING_PKG,ProductTypes.FINISHED_GOOD,ProductTypes.SERVICE_PRODUCT});
		List<Product> products = new ArrayList<Product>();
		for(ProductCategory prod:pc) {
			Product product = new Product();
			BeanUtils.copyProperties(prod.getProduct(), product);
			ProductContent content = prodContentRepo.findFirstByProductIdAndType(product.getId(),ProductContentTypes.IMAGE);
			if(content!=null) {
				product.setImage(content.getContent().getResource().getInfo());
			}
			products.add(product);
		}
		return products;
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
	public CatalogResponse getCategory(String id) {
		CatalogResponse response = new CatalogResponse();
		
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
				response.setCategories(categories);
			}
		}else {
			response.setStatus(-1);
			response.setMessage("No category found");
		}
		return response;
	}

	@Override
	public ProductResponse getCategoryProducts(String id) {
		ProductResponse response = new ProductResponse();
		response.setProducts(getProductsByCategory(id));
		return response;
	}
}
