package jason.app.brainstorm.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY_ROLLUP")
@IdClass(CategoryRollupKey.class)
public class CategoryRollup {
	
	@Id
	private Category category;
	
	@Id
	private String parentCategory;
	
	@Id
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
