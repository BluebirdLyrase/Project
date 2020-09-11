package stackoverflow.database;

import java.io.IOException;
import java.util.logging.Logger;

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

import stackoverflow.APIConnecter.StackOverFlowConnecter;
import stackoverflow.LocalJsonConnector.LocalJsonList;
import stackoverflow.LocalJsonConnector.Log;

public class Account extends LocalJsonList {
	protected static final Logger LOGGER = Logger.getLogger(StackOverFlowConnecter.class.getName());
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
		newData.put("login", true);
		JSONArray newArray = new JSONArray();
		newArray.put(newData);
		JSONObject newJSONObject = new JSONObject();
		newJSONObject.put(arrayName, newArray);
		saveJSONFile(filePath, newJSONObject);
		LOGGER.info("Connected to : "+DatabaseURL+" as :"+userID);
	}
	
	public String getDatabaseURL() throws JSONException {
		String url = null;
		JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(0);
		url = object.getString("databaseURL");
		return url;
	}
	
	public String getUserID() throws JSONException {
		String userID = null;
		JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(0);
		userID = object.getString("userID");
		return userID;
	}
	
	public boolean isLoggedIn() throws JSONException {
		boolean result = false;
		JSONObject json ;
		int lenght = jsonObject.getJSONArray(arrayName).length();
		LOGGER.info("lenght:"+lenght);
		boolean login = false;
		if(lenght!=0) {
			json  = jsonObject.getJSONArray(arrayName).getJSONObject(0);
			login = json.getBoolean("login");
			LOGGER.info("login:"+login);
			if(login) {
				String userID = json.getString("userID");
				String responseBodyString = databaseReader("/api/user/"+userID);
				LOGGER.info("responseBodyString:"+responseBodyString);
				result = Boolean.parseBoolean(responseBodyString);
			}
		}
		return result;
	}
	
	public String getConnectionStatus(){
		String responseBodyString = databaseReader("/api/checkConnection");	
	    switch(responseBodyString) {
	    case "0": connectionstatus = "Disconnected" ; break;
	    case "1": connectionstatus = "Connected" ; break;
	    case "2": connectionstatus = "Connecting" ; break;
	    case "3": connectionstatus = "Disconnecting" ; break;
	    }
		return connectionstatus;
	}
	
	private String databaseReader(String apiURL) {	
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String responseBodyString = null;	
		try {
		String authenURL = getDatabaseURL() + apiURL;
		HttpPost request = new HttpPost(authenURL);
	    CloseableHttpResponse response = httpClient.execute(request);
	    HttpEntity responseBodyentity = response.getEntity();
	    responseBodyString = EntityUtils.toString(responseBodyentity);
		} catch (ParseException | IOException | JSONException e) {
			connectionstatus = "Unavailable";
			e.printStackTrace();
			new Log().saveLog(e);
		}
	    return responseBodyString;
	}
	
}
