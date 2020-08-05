package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;

public class SearchingHistoryDatabaseWriter extends DatabaseConnector {
	private String jsonString = "";
	private String collection = "ViewHistory";
	public SearchingHistoryDatabaseWriter(String tilte ,String ID ,String tag ,String date) throws JSONException, IOException {

		jsonString="{\"Title\":"+tilte+",\"ID\""+ID+",\"Tags\":["+tag+"],\"Date\":"+date+"}";
		databaseWriter(jsonString,collection);
		
	}

}
