package jason.app.brainstorm.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
}
