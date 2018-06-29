package jason.app.brainstorm.product.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class CatalogCategoryKey implements Serializable{

	@Column(name="PROD_CATALOG_ID")
	private String catalogId;
	

	@Column(name="PRODUCT_CATEGORY_ID")
	private String category;
	
	@Column(name="PROD_CATALOG_CATEGORY_TYPE_ID")
	private String type;
	
	@Column(name="FROM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate;

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	
}
