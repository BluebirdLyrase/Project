package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentTitleWriter extends LocalJsonList{
	
	public ContentTitleWriter() throws IOException, JSONException {
		super("OfflineContentList");
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
			e.printStackTrace();
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving ContentTitle : "+e);
			new Log().saveLog(e);
		}
	}
	
	@Override
	public boolean delete(int index) {
		boolean result = true;
		try {
			jsonObject.getJSONArray(arrayName).remove(index);
			saveJSONFile(filePath, jsonObject);
		} catch (JSONException | IOException e) {
			e.printStackTrace();
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while removing item." + e);
			new Log().saveLog(e);
		}
		
		return result;
	}

}
