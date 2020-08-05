package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;

public class UserDatabaseWriter extends DatabaseConnector {
	private String jsonString = "";
	private String collection = "User";
	public UserDatabaseWriter(String UserID ) throws JSONException, IOException {

		jsonString="{\"UserID\":"+UserID+"}";
		databaseWriter(jsonString,collection);
		
	}

}
