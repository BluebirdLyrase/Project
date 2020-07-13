package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class ContentTitleList extends LocalJsonList {

	public ContentTitleList() throws IOException, JSONException {
		super("OfflineContentList");
		this.lenght = jsonObject.getJSONArray(arrayName).length();
		title = new String[this.lenght];
		filename = new String[this.lenght];
		for (int i = 0; i < this.lenght; i++) {
			JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(i);
			title[i] = object.getString("Title");
			filename[i] = object.getString("Filename");
		}
	}

	private int lenght;
	private String[] title;
	private String[] filename;

	public String[] getTitle() {
		return title;
	}

	public String[] getFilename() {
		return filename;
	}

	public int getLenght() {
		return lenght;
	}

}
