package stackoverflow.Tester;

import java.io.IOException;
import java.net.http.HttpClient;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginAPiTest {
	
	public static void main(String[] args) throws JSONException, IOException {   
		JSONObject json = new JSONObject("{" + 
				"    \"UserID\" : \"xxx\"," + 
				"    \"Password\" : \"yyyllll\"" + 
				"}");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {	
		    HttpPost request = new HttpPost("http://localhost:8095/api/authen");
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    CloseableHttpResponse response = httpClient.execute(request);
		    HttpEntity responseBodyentity = response.getEntity();
		    String responseBodyString = EntityUtils.toString(responseBodyentity);
		    System.out.println(responseBodyString);
		} catch (Exception ex) {
			System.out.println(ex); //TODO Log properly
		} finally {
		    httpClient.close();
		}

	}

}
