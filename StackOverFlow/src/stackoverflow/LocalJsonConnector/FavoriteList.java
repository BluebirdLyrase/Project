package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteList extends LocalJsonList  {
	
	public FavoriteList() throws IOException, JSONException {
		super("Favorite");
		this.lenght = jsonObject.getJSONArray(arrayName).length();
		title = new String[this.lenght];
		id = new String[this.lenght];
		site = new String[this.lenght];
		for (int i = 0; i < this.lenght; i++) {
			JSONObject object = jsonObject.getJSONArray(arrayName).getJSONObject(i);
			title[i] = object.getString("Title");
			id[i] = object.getString("ID");
			site[i] = object.getString("Site");
		}
	}
	
	private int lenght;
	private String[] title;
	private String[] id;
	private String[] site;
	
	public String[] getTitle() {
		return title;
	}
	
	public String[] getSite() {
		return site;
	}

	public String[] getID() {
		return id;
	}

	public int getLenght() {
		return lenght;
	}

}
