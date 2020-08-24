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
		// TODO Auto-generated constructor stub
	}

	public void saveContentViewHistory(String id, String[] tags, String title){
		JSONObject newData = new JSONObject();
		JSONArray arrayTags = new JSONArray();
		for (int i = 0; i < tags.length; i++) {
			arrayTags.put(tags[i]);
		}
			String date = LocalDateTime.now().toString();
			try {
			newData.put("ID", id);
			newData.put("Tags", arrayTags);
			newData.put("Title", title);
			newData.put("Date", date);
			JSONArray newArray = jsonObject.getJSONArray(arrayName);
			newArray.put(newData);
			jsonObject.put(arrayName, newArray);
			saveJSONFile(filePath, jsonObject);
			///database///
			new ViewHistoryDatabaseWriter(newData);
			/////////////
			}catch (JSONException | IOException e) {
				e.printStackTrace();
				 //TODO: handle exception
			}
	}

}
