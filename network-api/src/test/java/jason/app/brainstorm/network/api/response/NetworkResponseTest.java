package jason.app.brainstorm.network.api.response;

import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NetworkResponseTest {
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testUnmarshalJson() {
		String data = "{\n" + 
				"  \"header\" : null,\n" + 
				"  \"body\" : {\n" + 
				"    \"sessionId\" : \"8b0ada95-1c76-4960-b9c2-a57ca9d8e853\",\n" + 
				"    \"csrfToken\" : \"1ecce723-6971-4877-8497-dce8d94d8688\"\n" + 
				"  }\n" + 
				"}\n" + 
				"";
		try {
			mapper.readValue(data, NetworkResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
