package jason.app.brainstorm.network.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Header {
	private AppHeader app;
	private ClientHeader client;
	private SecurityHeader security;
	public AppHeader getApp() {
		return app;
	}
	public void setApp(AppHeader app) {
		this.app = app;
	}
	public ClientHeader getClient() {
		return client;
	}
	public void setClient(ClientHeader client) {
		this.client = client;
	}
	public SecurityHeader getSecurity() {
		return security;
	}
	public void setSecurity(SecurityHeader security) {
		this.security = security;
	}
	
	
}
