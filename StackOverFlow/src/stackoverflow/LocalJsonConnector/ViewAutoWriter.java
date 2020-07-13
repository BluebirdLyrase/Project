package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewAutoWriter extends View {
	
	public ViewAutoWriter() throws IOException, JSONException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void saveContentViewHistory(String id,String[] tags,String title) throws JSONException, IOException {
		JSONObject newData = new JSONObject();
		JSONArray arrayTags = new JSONArray();
		for(int i = 0;i<tags.length;i++) {
			arrayTags.put(tags[i]);
		}
		newData.put("ID", id);
		newData.put("Tags",arrayTags);
		newData.put("Title", title);
		newData.put("Date",LocalDateTime.now().toString());
		
        JSONArray newArray = jsonObject.getJSONArray(arrayName);
        newArray.put(newData);
        jsonObject.put(arrayName,newArray);
        saveJSONFile(filePath, jsonObject);
	}

}
