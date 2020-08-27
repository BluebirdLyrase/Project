package stackoverflow.Tester.manueltest;

import java.io.IOException;
import java.net.http.HttpClient;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoApiTest {
	
	public static void main(String[] args) throws JSONException, IOException {   
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

		try {	
		    HttpPost request = new HttpPost("http://localhost:8095/api/addViewHistory");
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    CloseableHttpResponse response = httpClient.execute(request);
		    System.out.println(response);
		} catch (Exception ex) {
			System.out.println(ex); //TODO Log properly
		} finally {
		    httpClient.close();
		}

	}

}
