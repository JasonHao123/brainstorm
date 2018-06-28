package jason.app.brainstorm.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ELECTRONIC_TEXT")
public class ElectronicText {

	@Id
	@Column(name="DATA_RESOURCE_ID")
	private String id;
	
	@Column(name="TEXT_DATA")
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
