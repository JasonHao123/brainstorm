package jason.app.brainstorm.product.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY_CONTENT")
@IdClass(CategoryContentKey.class)
public class CategoryContent {
	@Id
	private String categoryId;
	
	@Id
	private Content content;
	
	@Id
	private String type;
	
	@Id
	private Date effectDate;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getEffectDate() {
		return effectDate;
	}

	public void setEffectDate(Date effectDate) {
		this.effectDate = effectDate;
	}
}
