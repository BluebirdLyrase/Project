package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalJsonList extends JSONFile {

	private String defaultDir = new DefaultDir().getDefaultDir();
	private File fileDir = new File(defaultDir + "\\StackOverFlowHelper");
	protected String filePath;
	protected String arrayName;
	protected JSONObject jsonObject;

	// message
	protected String delConfrimMsg = "Are you sure you want to delete this?";


	public LocalJsonList(String filename) {
		LOGGER.setLevel(Level.WARNING);
		this.filePath = defaultDir + "\\StackOverFlowHelper\\" + filename + ".json";
		arrayName = filename;

		// Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("Directory create" + fileDir.getName());
		} else {
			LOGGER.info("Directory already exists.");
		}

		File newFile = new File(filePath);
		// Check if there is already .json file if not create one
		try {
			if (newFile.createNewFile()) {
				LOGGER.info("File created : " + newFile.getName());
				Files.writeString(Paths.get(filePath), "{" + arrayName + ":[]}", StandardOpenOption.WRITE);
			} else {
				LOGGER.info("File already exists.");
			}
			jsonObject = parseJSONFile(filePath);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}
	}

	// delete the selection data locate by index parameter
	// and return boolean id user confirm deleting or not
	public void delete(int index) {
			try {
				jsonObject.getJSONArray(arrayName).remove(index);
				System.out.println(jsonObject);
				saveJSONFile(filePath, jsonObject);
			} catch (JSONException | IOException e) {
				new Log().saveLog(e);
				LOGGER.severe("Error while removing item." + e);
			}
		
	}

	public void clear() {
			deleteFile(filePath);
	}
	
	public double getSize() {
		return super.getSize(filePath);
	}


}
