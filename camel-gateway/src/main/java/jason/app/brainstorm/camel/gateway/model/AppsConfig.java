package jason.app.brainstorm.camel.gateway.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="apps")
public class AppsConfig {
	private List<String> app;

	public List<String> getApp() {
		return app;
	}

	public void setApp(List<String> app) {
		this.app = app;
	}
	
	
}
