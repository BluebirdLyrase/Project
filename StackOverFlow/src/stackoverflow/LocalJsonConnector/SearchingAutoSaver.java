package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchingAutoSaver extends Searching{

	public SearchingAutoSaver() throws IOException, JSONException {
		super();
	}
	
	public void saveSearchTextHistory(String SearchText) throws IOException, JSONException {
		JSONObject newData = new JSONObject();
		newData.put("Search Text", SearchText);
		newData.put("Date",LocalDateTime.now().toString());
		
        JSONArray newArray = jsonObject.getJSONArray(arrayName);
        newArray.put(newData);
        jsonObject.put(arrayName,newArray);
        saveJSONFile(filePath, jsonObject);
	}

}
