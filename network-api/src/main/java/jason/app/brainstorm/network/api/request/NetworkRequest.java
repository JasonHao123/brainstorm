package jason.app.brainstorm.network.api.request;

import jason.app.brainstorm.network.api.vo.Header;

public class NetworkRequest {
	private Header header;
	private Object body;
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
	
}
