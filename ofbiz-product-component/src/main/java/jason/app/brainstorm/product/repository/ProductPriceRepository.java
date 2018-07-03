package jason.app.brainstorm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.ProductPrice;
import jason.app.brainstorm.product.entity.ProductPriceKey;

public interface ProductPriceRepository extends JpaRepository<ProductPrice, ProductPriceKey> {
	List<ProductPrice> findByProductIdAndPurpose(String categoryId,String purpose);
}
