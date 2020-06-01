package prototype1.APIconnecter;

import java.io.IOException;

import org.json.JSONException;

public class TitleListFileReader extends JSONFileReader {

	public TitleListFileReader(String location) throws IOException, JSONException {
		this.location = location;
		this.json = readJsonFromFile(location);
	}
	
	public String[] getTitleList() throws JSONException {
		int titleLenght = json.getJSONArray("items").length();
		String[] titleList = new String[titleLenght];

		for (int i = 0; i < titleLenght; i++) {
			titleList[i] = json.getJSONArray("items").getJSONObject(i).get("title").toString();
		}

		return titleList;
	}
	
	public String getAll() {
		return json.toString();
	}

}
