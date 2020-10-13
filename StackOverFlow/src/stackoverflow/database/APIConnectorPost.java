package stackoverflow.database;
import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Log;

public class APIConnectorPost {
	private String apiResponse = "no response";
	protected Account account = new Account();
	protected String userID;
	
	public String databaseWriter(String json,String apiName)throws JSONException, IOException  {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			String baseURL= account.getDatabaseURL()+"/api/";
			String url = baseURL+apiName;
		    HttpPost request = new HttpPost(url);
		    StringEntity params = new StringEntity(json);
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    CloseableHttpResponse response = httpClient.execute(request);
		    HttpEntity entity = response.getEntity();
		    apiResponse = EntityUtils.toString(entity);
		    System.out.println("API response : "+ apiResponse);
		} catch (Exception ex) {
			ex.printStackTrace();
			new Log().saveLog(ex);
		} finally {
		    httpClient.close();
		}
		return apiResponse;
	}
}
