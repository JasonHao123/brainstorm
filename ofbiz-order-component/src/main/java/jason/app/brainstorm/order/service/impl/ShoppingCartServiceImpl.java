package jason.app.brainstorm.order.service.impl;

import jason.app.brainstorm.order.model.vo.Order;
import jason.app.brainstorm.order.model.vo.ShoppingCart;
import jason.app.brainstorm.order.model.vo.ShoppingItem;
import jason.app.brainstorm.order.service.ShoppingCartService;

public class ShoppingCartServiceImpl implements ShoppingCartService{

	@Override
	public ShoppingCart calcShoppingcart(ShoppingCart shoppingcart) {
		double total = 0;
		for(ShoppingItem item:shoppingcart.getItems()) {
			item.setPrice(item.getPrice()*0.9);
			total = total + item.getPrice()*item.getQuantity();
		}
		shoppingcart.setTotalAmount(total);
		return shoppingcart;
	}

	@Override
	public Order placeOrder(ShoppingCart body) {
		// TODO Auto-generated method stub
		return null;
	}

}
