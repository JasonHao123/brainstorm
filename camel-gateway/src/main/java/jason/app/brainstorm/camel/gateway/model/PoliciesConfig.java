package jason.app.brainstorm.camel.gateway.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="policies")
public class PoliciesConfig {
	private List<String> policy;

	public List<String> getPolicy() {
		return policy;
	}

	public void setPolicy(List<String> policy) {
		this.policy = policy;
	}


	
	
}
