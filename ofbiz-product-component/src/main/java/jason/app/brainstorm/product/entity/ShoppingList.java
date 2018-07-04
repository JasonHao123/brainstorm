package jason.app.brainstorm.product.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="SHOPPING_LIST")
public class ShoppingList {
	@Id
	@Column(name="SHOPPING_LIST_ID")
	private String id;
	
	@Column(name="SHOPPING_LIST_TYPE_ID")
	private String type;
	
	@Column(name="PARTY_ID")
	private String partyId;
	
	@Column(name="LIST_NAME")
	private String listName;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="IS_PUBLIC")
	private String isPublic;
	
	@Column(name="IS_ACTIVE")
	private String isActive;
	
	@Column(name="CURRENCY_UOM")
	private String currentCode;
	
	@OneToMany(mappedBy="id.list",fetch=FetchType.EAGER)
	private List<ShoppingListItem> items;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getCurrentCode() {
		return currentCode;
	}

	public void setCurrentCode(String currentCode) {
		this.currentCode = currentCode;
	}

	public List<ShoppingListItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingListItem> items) {
		this.items = items;
	}
	
	
	
}
