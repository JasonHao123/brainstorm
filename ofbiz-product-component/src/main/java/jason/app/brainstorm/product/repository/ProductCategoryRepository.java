package jason.app.brainstorm.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.Category;
import jason.app.brainstorm.product.entity.ProductCategory;
import jason.app.brainstorm.product.entity.ProductCategoryKey;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryKey> {

    /**
     * Find persons like first name.
     */
    public List<ProductCategory> findByCategoryInAndProduct_TypeIn(List<Category> categorys,String[] types);

}
