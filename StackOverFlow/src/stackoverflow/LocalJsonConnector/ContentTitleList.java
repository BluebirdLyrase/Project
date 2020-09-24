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
		site = new String[this.lenght];
		for (int i = 0; i < this.lenght; i++) {
			JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(i);
			title[i] = object.getString("Title");
			filename[i] = object.getString("Filename");
			site[i] = object.getString("Site");
		}
	}

	private int lenght;
	private String[] title;
	private String[] filename;
	private String[] site;

	public String[] getTitle() {
		return title;
	}

	public String[] getFilename() {
		return filename;
	}
	
	public String[] getSite() {
		return site;
	}

	public int getLenght() {
		return lenght;
	}

}
