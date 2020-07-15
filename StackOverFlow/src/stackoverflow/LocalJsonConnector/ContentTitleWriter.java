package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
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
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving ContentTitle : "+e);
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean delete(int index) {
		boolean result = true;
		try {
			jsonObject.getJSONArray(arrayName).remove(index);
			saveJSONFile(filePath, jsonObject);
		} catch (JSONException | IOException e) {
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while removing item." + e);
		}
		
		return result;
	}

}
