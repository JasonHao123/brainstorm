package jason.app.brainstorm.camel.gateway.model;

public class PolicyResult {
	// -1 Not support 0:OK 1:error
	private int status;
	private boolean encryption;
	private boolean proxyMode;
	private String module;
	private String url;
	private String errorMessage;
	private boolean https;

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
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isHttps() {
		return https;
	}
	public void setHttps(boolean https) {
		this.https = https;
	}

	
}
