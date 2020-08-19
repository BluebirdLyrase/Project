package stackoverflow.Tester;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class MongoApiTest {
	
	public static void main(String[] args) throws JSONException, IOException {   
//		URL url = new URL ("http://localhost:8095/api/addViewHistory");
//		HttpURLConnection con = (HttpURLConnection)url.openConnection();
//		con.setRequestMethod("POST");
//		con.setRequestProperty("Content-Type", "application/json; utf-8");
//		con.setDoOutput(true);
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
		    HttpPost request = new HttpPost("http://yoururl");
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    httpClient.execute(request);
		// handle response here...
		} catch (Exception ex) {
		    // handle exception here
		} finally {
		    httpClient.close();
		}
		
//		try(OutputStream os = con.getOutputStream()) {
//		    byte[] input = jsonInputString.getBytes("utf-8");
//		    os.write(input, 0, input.length);			
//		}
//		
//		try(BufferedReader br = new BufferedReader(
//				  new InputStreamReader(con.getInputStream(), "utf-8"))) {
//				    StringBuilder response = new StringBuilder();
//				    String responseLine = null;
//				    while ((responseLine = br.readLine()) != null) {
//				        response.append(responseLine.trim());
//				    }
//				    System.out.println(response.toString());
//				}
		
		



	}

}
