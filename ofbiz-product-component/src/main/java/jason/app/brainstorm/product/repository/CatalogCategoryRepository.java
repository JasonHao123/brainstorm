package jason.app.brainstorm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.CatalogCategory;
import jason.app.brainstorm.product.entity.CatalogCategoryKey;

public interface CatalogCategoryRepository extends JpaRepository<CatalogCategory, CatalogCategoryKey> {

    /**
     * Find persons like first name.
     */
    public CatalogCategory findFirstByCatalogIdAndType(String catalogId,String type);

}
