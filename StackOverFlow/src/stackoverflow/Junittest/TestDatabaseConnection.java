package stackoverflow.Junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class TestDatabaseConnection {

	@Test
	void test() throws JSONException, IOException {
		CloseableHttpResponse expect;
		JSONObject json = new JSONObject("{" + 
				"    \"UserID\" : \"xxx\"," + 
				"    \"Title\" : \"NetworkOnMainThreadException when I change HTTP to HTTPS in AsyncTask or Thread\"," + 
				"    \"ID\" : \"29035924\"," + 
				"    \"Tags\" : [ " + 
				"        \"android\", " + 
				"        \"multithreading\", " + 
				"        \"android-asynctask\"" + 
				"    ]," + 
				"    \"Date\" : \"2020-08-18T11:37:08.517275\"" + 
				"}");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
	    HttpPost request = new HttpPost("http://localhost:8095/api/addViewHistory");
	    StringEntity params = new StringEntity(json.toString());
	    request.addHeader("content-type", "application/json");
	    request.setEntity(params);
	    CloseableHttpResponse response = httpClient.execute(request);
	    assertEquals("200 OK HTTP/1.1",response.toString());
		httpClient.close();
	}

}
