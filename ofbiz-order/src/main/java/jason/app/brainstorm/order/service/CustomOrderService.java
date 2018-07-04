package jason.app.brainstorm.order.service;

import org.apache.camel.Exchange;

public interface CustomOrderService {
	public void setCurrentUserHeader(Exchange exchange);
}
