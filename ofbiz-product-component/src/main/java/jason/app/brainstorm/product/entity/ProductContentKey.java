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
public class ProductContentKey implements Serializable{
	@Column(name="PRODUCT_ID")
	private String productId;
	
	@ManyToOne
	@JoinColumn(name="CONTENT_ID")
	private Content content;
	
	@Column(name="PRODUCT_CONTENT_TYPE_ID")
	private String type;
	
	@Column(name="FROM_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectDate;

	
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
