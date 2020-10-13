package stackoverflow.database;
import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.json.JSONObject;
import stackoverflow.LocalJsonConnector.Log;
public class APIConnectorGet {
		protected Account account = new Account();
		protected JSONObject json;

		protected JSONObject readJsonFromUrl(String apiName) throws IOException {

			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			try {
				String baseURL= account.getDatabaseURL()+"/api/";
				String url = baseURL+apiName;
			    HttpGet request = new HttpGet(url);
			    request.addHeader("content-type", "application/json");
			    CloseableHttpResponse response = httpClient.execute(request);
			    HttpEntity entity = response.getEntity();
			    String jsonText = EntityUtils.toString(entity);
			    json = new JSONObject(jsonText);
			} catch (Exception ex) {
				ex.printStackTrace();
				new Log().saveLog(ex);
			} finally {
			    httpClient.close();
			}
			return json;
			
				

		}

}
