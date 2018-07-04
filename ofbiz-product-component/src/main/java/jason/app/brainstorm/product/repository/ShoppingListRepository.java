package jason.app.brainstorm.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jason.app.brainstorm.product.entity.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, String> {
	ShoppingList findFirstByPartyIdAndType(String party,String type);
}
