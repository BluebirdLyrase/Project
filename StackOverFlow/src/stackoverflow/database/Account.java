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

import stackoverflow.APIConnecter.StackExchangeConnecter;
import stackoverflow.LocalJsonConnector.LocalJsonList;
import stackoverflow.LocalJsonConnector.Log;

public class Account extends LocalJsonList {
	protected static final Logger LOGGER = Logger.getLogger(StackExchangeConnecter.class.getName());

	public Account() {
		super("Account");
	}

	private String DatabaseURL;
	private String userID;
	private String password;
	private String LoginMSG;
	private String success = "Successfully logged in";
	private String wrong = "Incorrect username or password";
	private String error = "Server unavailable";
	private String connectionstatus;

	public void Logout() {
		LOGGER.info("Logout");
		if (haveAccount()) {
			JSONObject newJSONObject = jsonObject;
			try {
				newJSONObject.getJSONArray(arrayName).getJSONObject(0).remove("login");
				newJSONObject.getJSONArray(arrayName).getJSONObject(0).put("login", false);
				saveJSONFile(filePath, newJSONObject);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
				new Log().saveLog(e);
			}
		}
	}

	public boolean haveAccount() {
		int lenght;
		boolean result = false;
		try {
			lenght = jsonObject.getJSONArray(arrayName).length();
			LOGGER.info("lenght:" + lenght);
			result = (lenght != 0);
		} catch (JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}
		return result;

	}

	public String Loggin(String userID, String password, String DatabaseURL) throws IOException, JSONException {
//		this.DatabaseURL = "http://" + DatabaseURL.replaceAll("/", "").replaceAll("http:", "");// remove "http://" if
																								// user already type it
		this.DatabaseURL = DatabaseURL;
		JSONObject json = new JSONObject(
				"{" + "    \"UserID\" : \"" + userID + "\"," + "    \"Password\" : \"" + password + "\"" + "}");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			String authenURL = this.DatabaseURL + "/api/authen";
			HttpPost request = new HttpPost(authenURL);
			StringEntity params = new StringEntity(json.toString());
			request.addHeader("content-type", "application/json");
			request.setEntity(params);
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity responseBodyentity = response.getEntity();
			boolean responseBodyString = Boolean.parseBoolean(EntityUtils.toString(responseBodyentity));
			if (responseBodyString) {
				LoginMSG = success;
				this.userID = userID; 
				//TODO encrypt password
				this.password = password;
				setDatabase();
			} else {
				LoginMSG = wrong;
				LOGGER.severe(responseBodyString + ":: incorrect username / password  " + this.DatabaseURL);

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
		newData.put("password", password);
		newData.put("login", true);
		JSONArray newArray = new JSONArray();
		newArray.put(newData);
		JSONObject newJSONObject = new JSONObject();
		newJSONObject.put(arrayName, newArray);
		saveJSONFile(filePath, newJSONObject);
		LOGGER.info("Connected to : " + DatabaseURL + " as :" + userID);
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

	public boolean isLoggedIn() {
		boolean result = false;
		JSONObject json;
		boolean login = false;
//		CloseableHttpClient httpClient = null;
		try {
		// Check if Account.json have any Data
		if (haveAccount()) {
			json = jsonObject.getJSONArray(arrayName).getJSONObject(0);
			login = json.getBoolean("login");
			LOGGER.info("login:" + login);
			// Check login status in local file
			if (login) {
				CloseableHttpClient httpClient = HttpClientBuilder.create().build();
				
				//TODO decrypt password
					String userID = json.getString("userID");
					String password = json.getString("password");
					String databaseURL = json.getString("databaseURL");
					JSONObject bodyjson = new JSONObject(
							"{" + "    \"UserID\" : \"" + userID + "\"," + "    \"Password\" : \"" + password + "\"" + "}");

					String authenURL = databaseURL + "/api/authen";
					HttpPost request = new HttpPost(authenURL);
					LOGGER.info(authenURL);
					StringEntity params = new StringEntity(bodyjson.toString());
					request.addHeader("content-type", "application/json");
					request.setEntity(params);
					CloseableHttpResponse response = httpClient.execute(request);
					HttpEntity responseBodyentity = response.getEntity();
					boolean responseBodyString = Boolean.parseBoolean(EntityUtils.toString(responseBodyentity));
					if (responseBodyString) {
						result  = true;
					} else {
						LoginMSG = wrong;
						LOGGER.severe(responseBodyString + ":: incorrect username / password  " + this.DatabaseURL);

					}
					httpClient.close();
			}
			}
		} catch ( JSONException | IOException | ParseException e) {
			LoginMSG = error;
			e.printStackTrace();
			new Log().saveLog(e);
		}
		
		return result;
	}

	public String getConnectionStatus() {
		String responseBodyString;
		try {
			responseBodyString = databaseReader("/api/checkConnection");

			switch (responseBodyString) {
			case "0":
				connectionstatus = "Disconnected";
				break;
			case "1":
				connectionstatus = "Connected";
				break;
			case "2":
				connectionstatus = "Connecting";
				break;
			case "3":
				connectionstatus = "Disconnecting";
				break;
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			connectionstatus = "Service Unavailable";
		}
		return connectionstatus;
	}

	private String databaseReader(String apiURL) throws IOException, ParseException {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		String responseBodyString = null;

		String authenURL;
		try {
			authenURL = getDatabaseURL() + apiURL;

			HttpPost request = new HttpPost(authenURL);
			CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity responseBodyentity = response.getEntity();
			responseBodyString = EntityUtils.toString(responseBodyentity);
		} catch (JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}

		return responseBodyString;
	}

}
