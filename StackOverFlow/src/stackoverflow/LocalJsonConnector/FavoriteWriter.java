package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteWriter extends LocalJsonList {
	
	public FavoriteWriter() throws IOException, JSONException {
		super("Favorite");
	}

	public String saveFavorite(String title, String id,String site) {
			String result;
		try {
			int lenght = jsonObject.getJSONArray(arrayName).length();
			boolean isNotDuplicate = true;
			for (int i = 0; i < lenght; i++) {
				int intID = jsonObject.getJSONArray(arrayName).getJSONObject(i).getInt("ID");
				String currentID = Integer.toString(intID);
				if (id.equals(currentID)) {
					isNotDuplicate = false;
					break;
				}

			}

			if (isNotDuplicate) {
				JSONObject newData = new JSONObject();
				newData.put("Title", title);
				newData.put("ID", id);
				newData.put("Site", site);
				JSONArray newArray = jsonObject.getJSONArray(arrayName);
				newArray.put(newData);
				jsonObject.put(arrayName, newArray);
				saveJSONFile(filePath, jsonObject);
				result = "Successfully saved to Favortie";
			} else {
				result = "Already saved this Question as Favorite" ;
				LOGGER.info("duplicate id");
			}
		} catch (JSONException | IOException e) {
			result = "Error while saving to Favorite";
			LOGGER.severe("Error while saving Favorite : " + e);
			new Log().saveLog(e);
		}
		return result;
	}

}
