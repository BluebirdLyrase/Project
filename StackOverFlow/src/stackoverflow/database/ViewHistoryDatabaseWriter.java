package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHistoryDatabaseWriter extends DatabaseConnector {
	private String jsonString = "";
	private String apiName = "addViewHistory";
	public ViewHistoryDatabaseWriter(JSONObject newData) throws JSONException, IOException {
		JSONObject json = newData ;
		json.put("UserID","xxx");
		jsonString = json.toString();
		databaseWriter(jsonString,apiName);
	}

}
