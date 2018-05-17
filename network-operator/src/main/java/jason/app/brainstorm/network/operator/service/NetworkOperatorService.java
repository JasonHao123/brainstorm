package jason.app.brainstorm.network.operator.service;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface NetworkOperatorService extends Predicate{
	
	public void recordFailure(Exchange exchange);
	
	public void handleServiceUrl(Exchange exchange);
	
	public void updateHeader(Exchange exchange) throws IOException;
	
	public void detectIpChange(Exchange exchange) throws IllegalAccessException;

}
