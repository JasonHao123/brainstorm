package jason.app.brainstorm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.CategoryRollup;
import jason.app.brainstorm.product.entity.CategoryRollupKey;

public interface CategoryRollupRepository extends JpaRepository<CategoryRollup, CategoryRollupKey> {

    /**
     * Find persons like first name.
     */
    public List<CategoryRollup> findByParentCategory(String parent);

}
