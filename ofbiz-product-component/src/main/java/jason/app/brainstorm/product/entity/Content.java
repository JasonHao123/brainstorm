package jason.app.brainstorm.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="CONTENT")
public class Content {
	@Id
	@Column(name="CONTENT_ID")
	private String id;
	
	@OneToOne
	@JoinColumn(name="DATA_RESOURCE_ID")
	private Resource resource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
	
	
}
