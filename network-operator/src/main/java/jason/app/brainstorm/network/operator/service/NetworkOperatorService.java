package jason.app.brainstorm.network.operator.service;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public interface NetworkOperatorService extends Predicate{
	
	public void recordFailure(Exchange exchange);
	
	public void handleServiceUrl(Exchange exchange);
	
	public void setNounce(Exchange exchange);
	
	public void detectIpChange(Exchange exchange) throws IllegalAccessException;

}
