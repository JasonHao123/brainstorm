package jason.app.brainstorm.product.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SHOPPING_LIST_ITEM")
public class ShoppingListItem {

	@EmbeddedId
	private ShoppingListItemKey id;
	
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private Product product;
	
	@Column(name="QUANTITY")
	private Double quantity;
	
	@Column(name="MODIFIED_PRICE")
	private Double price;


	public ShoppingListItemKey getId() {
		return id;
	}

	public void setId(ShoppingListItemKey id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
