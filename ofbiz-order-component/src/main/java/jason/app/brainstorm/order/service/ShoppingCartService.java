package jason.app.brainstorm.order.service;

import jason.app.brainstorm.order.model.vo.Order;
import jason.app.brainstorm.order.model.vo.ShoppingCart;

public interface ShoppingCartService {

	ShoppingCart calcShoppingcart(ShoppingCart shoppingcart);

	Order placeOrder(ShoppingCart body);

}
