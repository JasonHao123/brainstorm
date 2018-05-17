package jason.app.brainstorm.network.api.request;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NetworkRequestTest {
	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void testUnmarshalJson() {
		String data = "{\n" + 
				"	\"header\":{\n" + 
				"		\"app\": {\n" + 
				"			\"system\":\"OFBIZ\",\n" + 
				"			\"country\":\"cn\",\n" + 
				"			\"version\":\"1.0\",\n" + 
				"			\"time\":\"2009-06-15T13:45:30\",\n" + 
				"			\"requestId\":\"cn-1.0sit-asdfasdfasd2223132\"\n" + 
				"		},\n" + 
				"		\"client\": {\n" + 
				"			\"platform\":\"Android\",\n" + 
				"			\"model\":\"Samsung S8\",\n" + 
				"			\"version\":\"6.0.1\",\n" + 
				"			\"deviceId\":\"3232423423423\"\n" + 
				"		},\n" + 
				"		\"security\": {\n" + 
				"			\"session\":\"809660bc-8eeb-4da3-8087-5277ac1953e7\",\n" + 
				"			\"nounce\":\"55d535b8-29cd-4d99-9792-5a9278b67598\"\n" + 
				"		}\n" + 
				"	},\n" + 
				"	\"body\": {\n" + 
				"		\"hello\":\"world\"\n" + 
				"	}\n" + 
				"	\n" + 
				"}\n" + 
				"\n" + 
				"";
				try {
					NetworkRequest request = mapper.readValue(data, NetworkRequest.class);
					assertNotNull(request.getBody());
				} catch (Exception e) {
					fail(e.getMessage());				
				}
	}

}
