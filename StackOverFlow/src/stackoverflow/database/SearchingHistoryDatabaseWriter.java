package stackoverflow.database;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchingHistoryDatabaseWriter extends DatabaseConnector {
	private String jsonString = "";
	private String collection = "SearchingHistory";
//	public SearchingHistoryDatabaseWriter(String tilte ,String ID ,String tag ,String date) throws JSONException, IOException {
////TODO
//		jsonString="{\"Title\":\""+tilte+"\",\"ID\"\""+ID+"\",\"Tags\":[\""+tag+"\"],\"Date\":\""+date+"\"}";
//		databaseWriter(jsonString,collection);
//		
//	}

	public SearchingHistoryDatabaseWriter(JSONObject newdata)throws JSONException, IOException {
		JSONObject json = newdata;
		json.put("UserID","xxx");
		jsonString = json.toString();
		databaseWriter(jsonString, collection);
	}

}
