package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;

public class ViewHistoryDatabaseWriter extends DatabaseConnector {
	private String jsonString = "";
	private String collection = "ViewHistory";
	public ViewHistoryDatabaseWriter(String tilte ,String ID ,String tag ,String date) throws JSONException, IOException {
		jsonString="{\"Title\":"+tilte+",\"ID\""+ID+",\"Tags\":["+tag+"],\"Date\":"+date+"}";
		// TODO Auto-generated constructor stub
		databaseWriter(jsonString,collection);
	}

}
