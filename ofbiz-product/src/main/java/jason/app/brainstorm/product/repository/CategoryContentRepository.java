package jason.app.brainstorm.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.CategoryContent;
import jason.app.brainstorm.product.entity.CategoryContentKey;

public interface CategoryContentRepository extends JpaRepository<CategoryContent, CategoryContentKey> {
	CategoryContent findFirstByCategoryIdAndType(String categoryId,String type);
}
