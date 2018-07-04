package jason.app.brainstorm.order.service.impl;

import org.apache.camel.Exchange;
import org.apache.camel.http.common.HttpMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jason.app.brainstorm.order.service.CustomOrderService;
import jason.app.brainstorm.security.model.OfbizUser;

@Service
public class CustomOrderServiceImpl implements CustomOrderService {

	@Override
	public void setCurrentUserHeader(Exchange exchange) {
		if(exchange.getMessage() instanceof HttpMessage) {
			HttpMessage msg = (HttpMessage)exchange.getMessage();
			System.out.println(msg.getRequest().getSession(false).getId());
		}
//		OfbizUser user = (OfbizUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
//		exchange.getMessage().setHeader("partyId", user.getPartyId());
	}

}
