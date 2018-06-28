package jason.app.brainstorm.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jason.app.brainstorm.product.entity.Category;

@Embeddable
public class CategoryRollupKey implements Serializable{
	@ManyToOne
	@JoinColumn(name="PRODUCT_CATEGORY_ID")
	private Category category;
	
	@Column(name="PARENT_PRODUCT_CATEGORY_ID")
	private String parentCategory;
	
	@Column(name="FROM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
	
	
}
