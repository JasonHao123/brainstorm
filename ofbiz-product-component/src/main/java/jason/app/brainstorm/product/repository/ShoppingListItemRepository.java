package jason.app.brainstorm.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.ShoppingList;
import jason.app.brainstorm.product.entity.ShoppingListItem;
import jason.app.brainstorm.product.entity.ShoppingListItemKey;

public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, ShoppingListItemKey> {

}
