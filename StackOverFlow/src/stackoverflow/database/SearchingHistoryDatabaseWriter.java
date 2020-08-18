package stackoverflow.database;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchingHistoryDatabaseWriter extends DatabaseConnector {
	private String jsonString = "";
	private String collection = "searchinghistories";

	public SearchingHistoryDatabaseWriter(JSONObject newdata)throws JSONException, IOException {
		JSONObject json = newdata;
		json.put("UserID","xxx");
		jsonString = json.toString();
		databaseWriter(jsonString, collection);
	}

}
