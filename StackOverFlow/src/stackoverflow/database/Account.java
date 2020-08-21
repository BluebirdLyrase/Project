package stackoverflow.database;

import java.io.IOException;

import javax.swing.JFileChooser;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.LocalJsonConnector.LocalJsonList;

public class Account extends LocalJsonList {
	
	public Account() {
		super("Account");
	}

	private String DatabaseURL;
	private String userID;

	public boolean Logout() {
		
		boolean result = true;
//		IWorkbench wb = PlatformUI.getWorkbench();
//		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
//		result = MessageDialog.openConfirm(win.getShell(), "Atention", "Are you sure you want to Log-Out?");
		if (result) {
			deleteFile(filePath);
//			MessageDialog.openInformation(win.getShell(), "Atention", "Log-Out Successfully");
		}
		return result;
	}
	
	public void Loggin(String userID,String password,String DatabaseURL) throws IOException, JSONException{
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
		    	this.userID = userID;
		    	setDatabase();
		    	}else {
		    		LOGGER.severe(responseBodyString+":: incorrect username / password  " + this.DatabaseURL);
		    		//TODO else properly
		    	}
		} catch (Exception ex) {
			System.out.println(ex); //TODO Log properly
		} finally {
		    httpClient.close();
		}
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
	
//	public boolean getConnectionStatus() {
//		
//	}
	
}
