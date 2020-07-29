package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentWriter extends Content{
	
	private boolean isSave;
	private String filePath;
	private JSONObject jsonObject;

	public ContentWriter()throws IOException,JSONException{
		super();
	}
	
	public boolean saveContent(JSONObject jsonObject,String id,String title) {
		this.jsonObject = jsonObject;
		filePath = fileDirURL + "//" + id + ".json";
		File newFile = new File(filePath);
		//Check if there is already .json file
		try {
			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			if (newFile.createNewFile()) {
				MessageDialog.openInformation(
						win.getShell(),
						"StackOverFlow",
						"Save to Offline Storage Sucessful");
				LOGGER.info("File created : " + newFile.getName());
				Files.writeString(Paths.get(filePath), "", StandardOpenOption.WRITE);
				writeContent();
				new ContentTitleWriter().saveContentTitle(title,id);
			} else {
				MessageDialog.openInformation(
						win.getShell(),
						"StackOverFlow",
						"Duplicate Qeustion in Offline Storage");
				LOGGER.info("File already exists : "+filePath);
			}
		} catch (IOException | JSONException e) {
			LOGGER.severe("Error while creating new json in Content : "+e);
			isSave = false;
		}
		return isSave;
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
