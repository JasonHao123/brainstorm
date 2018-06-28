package jason.app.brainstorm.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY_MEMBER")
@IdClass(ProductCategoryKey.class)
public class ProductCategory {

	@Id
	private Category category;
	

	@Id
	private Product product;

	
	@Id
	private Date effectDate;



	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}


	public Date getEffectDate() {
		return effectDate;
	}


	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	
	
}
