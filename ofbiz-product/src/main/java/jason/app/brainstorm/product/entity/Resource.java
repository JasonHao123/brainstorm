package jason.app.brainstorm.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DATA_RESOURCE")
public class Resource {

	@Id
	@Column(name="DATA_RESOURCE_ID")
	private String id;
	
	@Column(name="DATA_RESOURCE_NAME")
	private String name;
	
	@Column(name="OBJECT_INFO")
	private String info;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
	
}
