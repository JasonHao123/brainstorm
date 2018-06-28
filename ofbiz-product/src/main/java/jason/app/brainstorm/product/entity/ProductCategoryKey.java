package jason.app.brainstorm.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ProductCategoryKey implements Serializable{

	@ManyToOne
	@JoinColumn(name="PRODUCT_CATEGORY_ID")
	private Category category;
	

	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private Product product;

	
	@Column(name="FROM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
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
