package jason.app.brainstorm.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY")
public class Category {

	@Id
	@Column(name="PRODUCT_CATEGORY_ID")
	private String id;
	
	@Column(name="PRODUCT_CATEGORY_TYPE_ID")
	private String type;
	
	@Column(name="CATEGORY_NAME")
	private String name;
	
	@Column(name="DESCRIPTION")
	private String description;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
