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

	public String[] getOrder() {
		return order;
	}

	public String[] getSort() {
		return sort;
	}

	public String[] getSite() {
		return site;
	}

	public String[] getTagged() {
		return tagged;
	}
	
	public String[] searchText;
	public String[] order;
	public String[] sort;
	public String[] site;
	public String[] tagged;
	public String[] searchingDate;

	public SearchingHistory() throws IOException, JSONException {
		super();
		int lenght = jsonObject.getJSONArray(arrayName).length();
		searchText = new String[lenght];
		searchingDate = new String[lenght];
		order = new String[lenght];
		sort = new String[lenght];
		site = new String[lenght];
		tagged = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(i);
			searchText[i] = object.getString("Search Text");
			searchingDate[i] = object.getString("Date");
			order[i] = object.getString("Order");
			sort[i] = object.getString("Sort By");
			site[i] = object.getString("Site");
			tagged[i] = object.getString("Tagged");
		}

	}

}
