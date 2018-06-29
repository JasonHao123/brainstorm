package jason.app.brainstorm.product.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jason.app.brainstorm.product.model.vo.Category;
import jason.app.brainstorm.product.model.vo.Product;

@JsonInclude(Include.NON_NULL)
public class ProductResponse {
	private int status;
	private String message;
	private List<Product> products;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}


}
