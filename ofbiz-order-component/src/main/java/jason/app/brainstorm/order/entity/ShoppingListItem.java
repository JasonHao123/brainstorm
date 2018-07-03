package jason.app.brainstorm.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import jason.app.brainstorm.order.model.vo.Product;

@Entity
@Table(name="SHOPPING_LIST_ITEM")
@IdClass(ShoppingListItemKey.class)
public class ShoppingListItem {

	@Id
	private ShoppingList list;
	
	@Id
	private String seqId;

	@Column(name="PRODUCT_ID")
	private String productId;
	
	@Column(name="QUANTITY")
	private Double quantity;
	
	@Column(name="MODIFIED_PRICE")
	private Double price;

	public ShoppingList getList() {
		return list;
	}

	public void setList(ShoppingList list) {
		this.list = list;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
}
