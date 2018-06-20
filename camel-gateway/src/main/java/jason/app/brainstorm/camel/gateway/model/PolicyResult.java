package jason.app.brainstorm.camel.gateway.model;

public class PolicyResult {
	// -1 Not support 0:OK 1:error
	private int status;
	private boolean encryption;
	private boolean proxyMode;
	private String route;
	private String errorMessage;
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public boolean isEncryption() {
		return encryption;
	}
	public void setEncryption(boolean encryption) {
		this.encryption = encryption;
	}
	public boolean isProxyMode() {
		return proxyMode;
	}
	public void setProxyMode(boolean proxyMode) {
		this.proxyMode = proxyMode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}
