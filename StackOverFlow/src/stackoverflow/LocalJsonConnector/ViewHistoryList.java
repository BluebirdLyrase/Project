package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHistoryList extends LocalJsonList {
	
	private String[] id;
	private String[] tags;
	private String[] title;
	private String[] viewDate;
	private String[] site;
	private int lenght;

	public ViewHistoryList() throws IOException, JSONException {
		super("ViewHistory");
		lenght = jsonObject.getJSONArray(arrayName).length();
		id = new String[lenght];
		tags = new String[lenght];
		title = new String[lenght];
		viewDate = new String[lenght];
		site = new String[lenght];

		for (int i = 0; i < lenght; i++) {
			JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(i);
			id[i] = object.getString("ID");
			title[i] = object.getString("Title");
			viewDate[i] = object.getString("Date");
			
			int tagLenght = object.getJSONArray("Tags").length();
			String tag = "";
			for (int j = 0; j < tagLenght; j++) {
				tag = tag +" "+ object.getJSONArray("Tags").getString(j);
			}
			
			tags[i] = tag;
			site[i] = object.getString("Site");
			
		}

	}
	
	public int getLenght() {
		return lenght;
	}

	public String[] getId() {
		return id;
	}

	public String[] getTags() {
		return tags;
	}

	public String[] getTitle() {
		return title;
	}
	
	public String[] getSite() {
		return site;
	}

	public String[] getViewDate() {
		return viewDate;
	}

}
