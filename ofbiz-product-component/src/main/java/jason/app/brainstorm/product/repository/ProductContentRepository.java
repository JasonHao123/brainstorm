package jason.app.brainstorm.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.ProductContent;
import jason.app.brainstorm.product.entity.ProductContentKey;

public interface ProductContentRepository extends JpaRepository<ProductContent, ProductContentKey> {
	ProductContent findFirstByProductIdAndType(String categoryId,String type);
}
