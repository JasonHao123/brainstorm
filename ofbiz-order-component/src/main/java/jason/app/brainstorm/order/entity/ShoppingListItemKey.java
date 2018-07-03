package jason.app.brainstorm.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ShoppingListItemKey implements Serializable{

	@ManyToOne
	@JoinColumn(name="SHOPPING_LIST_ID")
	private ShoppingList list;
	
	@Column(name="SHOPPING_LIST_ITEM_SEQ_ID")
	private String seqId;

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public ShoppingList getList() {
		return list;
	}

	public void setList(ShoppingList list) {
		this.list = list;
	}
	
	
}
