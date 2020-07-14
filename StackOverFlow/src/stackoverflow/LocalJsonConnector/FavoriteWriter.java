package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteWriter extends LocalJsonList {

	public FavoriteWriter() throws IOException, JSONException {
		super("Favorite");
	}

	public void saveFavorite(String title, String id) {

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
				JSONArray newArray = jsonObject.getJSONArray(arrayName);
				newArray.put(newData);
				jsonObject.put(arrayName, newArray);
				saveJSONFile(filePath, jsonObject);
			} else {
				LOGGER.info("[" + LOGGER.getName() + "] " + "duplicate id");
			}
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving Favorite : " + e);
			e.printStackTrace();
		}
	}

}
