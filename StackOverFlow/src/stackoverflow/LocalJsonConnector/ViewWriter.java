package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.database.ViewHistoryDatabaseWriter;

public class ViewWriter extends LocalJsonList {

	public ViewWriter() throws IOException, JSONException {
		super("ViewHistory");
	}

	public void saveContentViewHistory(String id, String[] tags, String title,String site) throws JSONException, IOException{
		JSONObject newData = new JSONObject();
		JSONArray arrayTags = new JSONArray();
		for (int i = 0; i < tags.length; i++) {
			arrayTags.put(tags[i]);
		}
			String date = LocalDateTime.now().toString();
			newData.put("ID", id);
			newData.put("Tags", arrayTags);
			newData.put("Title", title);
			newData.put("Date", date);
			newData.put("Site", site);
			JSONArray newArray = jsonObject.getJSONArray(arrayName);
			newArray.put(newData);
			jsonObject.put(arrayName, newArray);
			try {
				saveJSONFile(filePath, jsonObject);
			} catch (JSONException | IOException e) {
				new Log().saveLog(e);
				e.printStackTrace();
			}
			///database///
			new ViewHistoryDatabaseWriter(newData);
			/////////////
	}

}
