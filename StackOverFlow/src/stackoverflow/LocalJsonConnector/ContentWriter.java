package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentWriter extends Content{

	public ContentWriter()throws IOException,JSONException{
		super();
	}
	
	public void saveContent(JSONObject itemObject) {
		
		try {
		JSONArray newArray = jsonObject.getJSONArray(arrayName);
        newArray.put(itemObject);
        jsonObject.put(arrayName,newArray);
        saveJSONFile(filePath, jsonObject);
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving Content : "+e);
			e.printStackTrace();
		}
	}
	
}
