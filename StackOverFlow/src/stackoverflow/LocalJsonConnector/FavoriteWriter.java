package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteWriter extends LocalJsonList {

	public FavoriteWriter() throws IOException, JSONException {
		super("Favorite");
	}
	
	public void saveFavorite(String title,String id) {
		JSONObject newData = new JSONObject();
		try {
		newData.put("Title", title);
		newData.put("ID", id);
        JSONArray newArray = jsonObject.getJSONArray(arrayName);
        newArray.put(newData);
        jsonObject.put(arrayName,newArray);
        saveJSONFile(filePath, jsonObject);
		}catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving Favorite : "+e);
			e.printStackTrace();
		}
	}

}
