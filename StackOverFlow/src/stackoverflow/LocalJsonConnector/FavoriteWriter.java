package stackoverflow.LocalJsonConnector;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavoriteWriter extends LocalJsonList {

	public FavoriteWriter() throws IOException, JSONException {
		super("Favorite");
	}

	public void saveFavorite(String title, String id) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
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
				MessageDialog.openInformation(
						win.getShell(),
						"StackOverFlow",
						"Save to Favortie Sucessful");
			} else {
				MessageDialog.openInformation(
						win.getShell(),
						"StackOverFlow",
						"Already add this Question as Favortie");
				LOGGER.info("[" + LOGGER.getName() + "] " + "duplicate id");
			}
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving Favorite : " + e);
			e.printStackTrace();
		}
	}

}
