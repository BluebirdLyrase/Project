package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentWriter extends Content{
	
	private String filePath;
	private JSONObject jsonObject;

	public ContentWriter()throws IOException,JSONException{
		super();
	}
	
	public String saveContent(JSONObject jsonObject,String id,String title) {
		String result;
		this.jsonObject = jsonObject;
		filePath = fileDirURL + "//" + id + ".json";
		File newFile = new File(filePath);
		//Check if there is already .json file
		try {
			if (newFile.createNewFile()) {
				result = "Save to Offline Storage Sucessful";
				LOGGER.info("File created : " + newFile.getName());
				Files.writeString(Paths.get(filePath), "", StandardOpenOption.WRITE);
				writeContent();
				new ContentTitleWriter().saveContentTitle(title,id);
			} else {
				result = "Duplicate Qeustion in Offline Storage";
				LOGGER.info("File already exists : "+filePath);
			}
		} catch (IOException | JSONException e) {
			result = "Error while saving to Offline mode";
			LOGGER.severe("Error while creating new json in Content : "+e);
		}
		return result;
	}
	
	private boolean writeContent() {
		boolean isWrite = true;
		try {
        saveJSONFile(filePath, jsonObject);
		} catch (JSONException | IOException e) {
			LOGGER.severe("Error while saving Content : "+e);
			new Log().saveLog(e);
			isWrite = false;
		}
		return isWrite;
	}


	

	
}
