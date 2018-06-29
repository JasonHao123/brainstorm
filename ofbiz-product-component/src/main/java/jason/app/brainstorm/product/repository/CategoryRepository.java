package jason.app.brainstorm.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.ElectronicText;

public interface CategoryRepository extends JpaRepository<ElectronicText, String> {
	
}
