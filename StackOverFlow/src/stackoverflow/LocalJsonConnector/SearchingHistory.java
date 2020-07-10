package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchingHistory extends Searching {
	
	public String[] getSearchText() {
		return searchText;
	}

	public String[] getSearchingDate() {
		return searchingDate;
	}

	public String[] searchText;
	public String[] searchingDate;
	
	public SearchingHistory() throws IOException, JSONException {
		super();
		int lenght = jsonObject.getJSONArray(arrayName).length();
		searchText = new String[lenght];
		searchingDate = new String[lenght];
		for(int i = 0;i<lenght;i++) {
			JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(i);
			searchText[i] = object.getString("Search Text");
			searchingDate[i] = object.getString("Date");
		}
		
	}
	
	


	
	
}
