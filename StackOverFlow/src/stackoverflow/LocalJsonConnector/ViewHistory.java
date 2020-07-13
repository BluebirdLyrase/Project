package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewHistory extends LocalJsonList {
	
	public String[] id;
	public String[] tags;
	public String[] title;
	public String[] viewDate;
	private int lenght;

	public ViewHistory() throws IOException, JSONException {
		super("ViewHistory");
		lenght = jsonObject.getJSONArray(arrayName).length();
		id = new String[lenght];
		tags = new String[lenght];
		title = new String[lenght];
		viewDate = new String[lenght];

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

	public String[] getViewDate() {
		return viewDate;
	}

}
