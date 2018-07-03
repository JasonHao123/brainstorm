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
public class ProductPriceKey implements Serializable{
	@Column(name="PRODUCT_ID")
	private String productId;

	@Column(name="PRODUCT_PRICE_PURPOSE_ID")
	private String purpose;
	
	@Column(name="PRODUCT_PRICE_TYPE_ID")
	private String type;
	
	@Column(name="CURRENCY_UOM_ID")
	private String currency;
	
	@Column(name="PRODUCT_STORE_GROUP_ID")
	private String storeGroupId;
	
	@Column(name="FROM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate;


	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStoreGroupId() {
		return storeGroupId;
	}

	public void setStoreGroupId(String storeGroupId) {
		this.storeGroupId = storeGroupId;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
