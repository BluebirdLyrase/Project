package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONException;
import org.json.JSONObject;

public class LocalJsonList extends JSONFile {

	private String defaultDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	private File fileDir = new File(defaultDir + "\\StackOverFlowHelper");
	protected String filePath;
	protected String arrayName;
	protected JSONObject jsonObject;

	// message
	protected String delConfrimMsg = "Are you sure you want to delete this?";
	protected String clearConfrimMsg ;
	protected String clearMsg ;


	public LocalJsonList(String filename) {
		LOGGER.setLevel(Level.ALL);
		this.filePath = defaultDir + "\\StackOverFlowHelper\\" + filename + ".json";
		arrayName = filename;
		
		clearConfrimMsg = "Do you want to remove all "+arrayName+" data?";
		clearMsg = "Succesfully remove all "+arrayName+" data";

		// Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory create" + fileDir.getName());
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory already exists.");
		}

		File newFile = new File(filePath);
		// Check if there is already .json file if not create one
		try {
			if (newFile.createNewFile()) {
				LOGGER.info("[" + LOGGER.getName() + "] " + "File created : " + newFile.getName());
				Files.writeString(Paths.get(filePath), "{" + arrayName + ":[]}", StandardOpenOption.WRITE);
			} else {
				LOGGER.info("[" + LOGGER.getName() + "] " + "File already exists.");
			}
			jsonObject = parseJSONFile(filePath);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}
	}

	// delete the selection data locate by index parameter
	// and return boolean id user confirm deleting or not
	public boolean delete(int index) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		boolean result = MessageDialog.openConfirm(win.getShell(), "Atention", delConfrimMsg);
		if (result) {
			try {
				jsonObject.getJSONArray(arrayName).remove(index);
				saveJSONFile(filePath, jsonObject);
			} catch (JSONException | IOException e) {
				new Log().saveLog(e);
				LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while removing item." + e);
			}
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Delete has been cancel by user.");
		}
		return result;
	}

	public boolean clear() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		boolean result = true;
		result = MessageDialog.openConfirm(win.getShell(), "Atention", clearConfrimMsg);
		if (result) {
			deleteFile(filePath);
			MessageDialog.openInformation(win.getShell(), "Atention", clearMsg);
		}
		return result;
	}
	
	public double getSize() {
		return super.getSize(filePath);
	}


}
