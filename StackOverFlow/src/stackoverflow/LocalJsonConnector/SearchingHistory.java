package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONException;

public class SearchingHistory extends Searching {
	
	public String[] getSearchText() {
		return searchText;
	}

	public String[] getSearchingDate() {
		return searchingDate;
	}

	public String[] searchText[];
	public String[] searchingDate;
	
	public SearchingHistory() throws IOException, JSONException {
		super();
		int lenght = jsonObject.getJSONArray(arrayName).length();
		searchText[] = new String[];
	}
	
	


	
	
}
