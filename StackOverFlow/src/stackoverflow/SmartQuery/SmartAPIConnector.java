package stackoverflow.SmartQuery;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartAPIConnector {

	protected JSONObject json;
	
	protected JSONObject readJsonFromUrl(String url) throws JSONException, IOException, ParseException {
		JSONObject result;
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
	    HttpGet request = new HttpGet(url);
	    request.addHeader("content-type", "application/json");
	    CloseableHttpResponse response;
		try {
			response = httpClient.execute(request);
	    HttpEntity entity = response.getEntity();
	    String responseText = EntityUtils.toString(entity);
	    String jsonText = "{\"result\":"+responseText+"}";
	    result = new JSONObject(jsonText);
		} finally {
		    httpClient.close();
		}

		return result;
	}
	
}
