package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;

import org.json.JSONException;
import org.json.JSONObject;

public class View extends JSONFile{
	
	private String defaultDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	private File fileDir = new File(filePath = defaultDir + "\\StackOverFlowHelper");
	protected String filePath = defaultDir + "\\StackOverFlowHelper\\ViewHistory.json";
	protected String arrayName = "ViewHistory" ;
	protected JSONObject jsonObject;
	
	public View() throws IOException, JSONException {
		
		//Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory create"+fileDir.getName());
		}
		else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory already exists.");
		}

		File newFile = new File(filePath);
		//Check if there is already .json file if not create one
		if (newFile.createNewFile()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "File created : " + newFile.getName());
			Files.writeString(Paths.get(filePath), "{"+arrayName+":[]}", StandardOpenOption.WRITE);
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "File already exists.");
		}
		jsonObject = parseJSONFile(filePath);
	}

}
