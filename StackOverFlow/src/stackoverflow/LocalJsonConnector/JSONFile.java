package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONFile {
	protected static final Logger LOGGER = Logger.getLogger(JSONFile.class.getName());
	
	public JSONObject parseJSONFile(String filePath) throws JSONException, IOException {
		String content = new String(Files.readAllBytes(Paths.get(filePath)));
		return new JSONObject(content);
	}

	protected void saveJSONFile(String filePath,JSONObject JsonObject) throws JSONException, IOException {
		Files.writeString(Paths.get(filePath), JsonObject.toString(), StandardOpenOption.WRITE);
	}

	protected void deleteFile(String filepath) {
		File f = new File(filepath);
		f.delete();
	}
	
	protected double getSize(String filepath) {
		double kilobytes = 0.000;
		File f = new File(filepath);
		if(f.exists()) {
		double bytes = f.length();
		kilobytes = (bytes / 1024);
		}
		return kilobytes;
	}

}
