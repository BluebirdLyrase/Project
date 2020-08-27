package stackoverflow.database;

import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.LocalJsonConnector.LocalJsonList;
import stackoverflow.LocalJsonConnector.Log;

public class Account extends LocalJsonList {
	
	public Account() {
		super("Account");
	}

	private String DatabaseURL;
	private String userID;
	private String LoginMSG ;
	private String success = "Successfully logged in";
	private String wrong = "Incorrect username ot password";
	private String error = "Server unavailable";
	private String connectionstatus;

	public void Logout() {
			deleteFile(filePath);
	}
	
	public String Loggin(String userID,String password,String DatabaseURL) throws IOException, JSONException{
		this.DatabaseURL = "http://"+DatabaseURL.replaceAll("/", "").replaceAll("http:", "");//remove "http://" if user already type it
		JSONObject json = new JSONObject("{" + 
				"    \"UserID\" : \""+userID+"\"," + 
				"    \"Password\" : \""+password+"\"" + 
				"}");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {	
			String authenURL = this.DatabaseURL +"/api/authen";
			HttpPost request = new HttpPost(authenURL);
		    StringEntity params = new StringEntity(json.toString());
		    request.addHeader("content-type", "application/json");
		    request.setEntity(params);
		    CloseableHttpResponse response = httpClient.execute(request);
		    HttpEntity responseBodyentity = response.getEntity();
		    String responseBodyString = EntityUtils.toString(responseBodyentity);
		    if(responseBodyString.equals("\"success\"")) {
		    	LoginMSG = success;
		    	this.userID = userID;
		    	setDatabase();
		    	}else {
		    		LoginMSG = wrong;
		    		LOGGER.severe(responseBodyString+":: incorrect username / password  " + this.DatabaseURL);
		    		
		    	}
		} catch (Exception e) {
			LoginMSG = error;
			e.printStackTrace();
			new Log().saveLog(e);
		} finally {
		    httpClient.close();
		}
		return LoginMSG;
	}
	
	private void setDatabase() throws JSONException, IOException {
		JSONObject newData = new JSONObject();
		newData.put("userID", userID);
		newData.put("databaseURL", DatabaseURL);
		JSONArray newArray = new JSONArray();
		newArray.put(newData);
		JSONObject newJSONObject = new JSONObject();
		newJSONObject.put(arrayName, newArray);
		saveJSONFile(filePath, newJSONObject);
		LOGGER.info("Connected to : "+DatabaseURL+" as :"+userID);
	}
	
	public String getDatabaseURL() throws JSONException {
		String url = null;
		if(isLoggedIn()) {
		JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(0);
		url = object.getString("databaseURL");
		}
		return url;
	}
	
	public String getUserID() throws JSONException {
		String userID = null;
		if(isLoggedIn()) {
		JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(0);
		userID = object.getString("userID");
		}
		return userID;
	}
	
	public boolean isLoggedIn() throws JSONException {
		int lenght = jsonObject.getJSONArray(arrayName).length();
		boolean result = false;
		if(lenght==1) {
			result = true;
		}
		return result;
		
	}
	
	public String getConnectionStatus(){
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		  try {
		String authenURL = getDatabaseURL() +"/api/checkConnection";
		HttpPost request = new HttpPost(authenURL);
	    CloseableHttpResponse response = httpClient.execute(request);
	    HttpEntity responseBodyentity = response.getEntity();
	    String responseBodyString = EntityUtils.toString(responseBodyentity);
	    switch(responseBodyString) {
	    case "0": connectionstatus = "Disconnected" ; break;
	    case "1": connectionstatus = "Connected" ; break;
	    case "2": connectionstatus = "Connecting" ; break;
	    case "3": connectionstatus = "Disconnecting" ; break;
	    }
		} catch (ParseException | IOException | JSONException e) {
			connectionstatus = "Unavailable";
			e.printStackTrace();
			new Log().saveLog(e);
		}
		
		return connectionstatus;
	}
	
}
