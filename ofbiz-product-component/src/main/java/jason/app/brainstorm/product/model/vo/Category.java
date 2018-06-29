package jason.app.brainstorm.product.model.vo;

import javax.persistence.Column;

public class Category {

	private String id;

	private String type;

	private String name;

	private String description;
	
	private String imageUrl;

	private String linkImage1;

	private String linkImage2;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLinkImage1() {
		return linkImage1;
	}

	public void setLinkImage1(String linkImage1) {
		this.linkImage1 = linkImage1;
	}

	public String getLinkImage2() {
		return linkImage2;
	}

	public void setLinkImage2(String linkImage2) {
		this.linkImage2 = linkImage2;
	}
	
	
}
