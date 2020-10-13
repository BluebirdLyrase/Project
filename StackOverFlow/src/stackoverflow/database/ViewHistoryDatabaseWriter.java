package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHistoryDatabaseWriter extends APIConnectorPost {
	
	private String jsonString = "";
	private String apiName = "addViewHistory";
	
	public ViewHistoryDatabaseWriter(JSONObject newData) throws JSONException, IOException {
		if(account.isLoggedIn()) {
		JSONObject json = newData ;
		userID = account.getUserID();
		json.put("UserID",userID);
		jsonString = json.toString();
		databaseWriter(jsonString,apiName);
	}else {
		//TODO
	}
	}

}
