package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentTitleWriter extends ContentTitle{
	
	public ContentTitleWriter() throws IOException, JSONException {
		super();
	}
	
	public void saveContentTitle(String title,String id) {
		JSONObject newData = new JSONObject();
		String filename = id+".json";
		try {
		newData.put("Title", title);
		newData.put("Filename", filename);
        JSONArray newArray = jsonObject.getJSONArray(arrayName);
        newArray.put(newData);
        jsonObject.put(arrayName,newArray);
        saveJSONFile(filePath, jsonObject);
		}catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving ContentTitle : "+e);
			e.printStackTrace();
		}
	}

}
