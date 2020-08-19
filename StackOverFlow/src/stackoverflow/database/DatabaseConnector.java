package stackoverflow.database;
import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;

public class DatabaseConnector {
	private String baseURL="http://localhost:8095/api/";
	public void databaseWriter(String json,String apiName)throws JSONException, IOException  {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {	
			String url = baseURL+apiName;
		    HttpPost request = new HttpPost(url);
		    StringEntity params = new StringEntity(json);
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
