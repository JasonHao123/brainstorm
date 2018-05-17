package jason.app.brainstorm.network.api.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SecurityHeader {
	private String session;
	private String nounce;
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getNounce() {
		return nounce;
	}
	public void setNounce(String nounce) {
		this.nounce = nounce;
	}
	
	
}
