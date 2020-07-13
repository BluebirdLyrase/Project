package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;

import org.json.JSONException;
import org.json.JSONObject;

public class Content extends JSONFile {

	private String defaultDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	protected String fileDirURL = (defaultDir + "\\StackOverFlowHelper\\OfflineContent");
	private File fileDir = new File(fileDirURL);
	protected String arrayName = "items";


	public Content() throws IOException, JSONException {
		//Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory create"+fileDir.getName());
		}
		else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory already exists.");
		}
	}

}
