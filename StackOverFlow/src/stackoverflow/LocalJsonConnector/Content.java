package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONException;

public class Content extends JSONFile {

	private String defaultDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	protected String fileDirURL = (defaultDir + "\\StackOverFlowHelper\\OfflineContent");
	private File fileDir = new File(fileDirURL);
	protected String arrayName = "items";
	
	protected String delConfrimMsg = "Are you sure you want to delete this?";
	protected String clearConfrimMsg = "Are you sure you want to Clear all Offline Content Data?";
	protected String clearMsg = "Succesfully clear all OfflineContent data";

	
	public Content() throws IOException, JSONException {
		LOGGER.setLevel(Level.SEVERE);
		// Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory create" + fileDir.getName());
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory already exists.");
		}
	}

	public boolean delete(String filename, int index) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		boolean result = MessageDialog.openConfirm(win.getShell(), "Atention", delConfrimMsg);
		if (result) {
			try {
				deleteFile(fileDirURL + "\\" + filename);
				new ContentTitleWriter().delete(index);
			} catch (IOException | JSONException e) {
				e.printStackTrace();
				new Log().saveLog(e);
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
				try {
					
					String[] entries = fileDir.list();
					for(String s: entries) {
						deleteFile(fileDirURL + "\\" + s);
					}
					new ContentTitleWriter().clear();
					MessageDialog.openInformation(win.getShell(), "Atention", clearMsg);
				} catch (IOException | JSONException e) {
					e.printStackTrace();
					new Log().saveLog(e);
				}
		}
		return result;
	}
	
	private String claerAllConfrimMsg = "Do you want to clear all data?" ;
	public boolean clearAll() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		boolean result = true;
		result = MessageDialog.openConfirm(win.getShell(), "Atention", claerAllConfrimMsg);
		if (result) {
			
		//Clear Every List
		File otherfileDir = new File(defaultDir + "\\StackOverFlowHelper");
		String[] otherEntries = otherfileDir.list();
		for(String s: otherEntries) {
			deleteFile(otherfileDir.getPath() + "\\" + s);
		}
		otherfileDir.delete();
		
		///Clear Offline Content
		String[] entries = fileDir.list();
		for(String s: entries) {
			deleteFile(fileDirURL + "\\" + s);
		}
		fileDir.delete();
		MessageDialog.openInformation(win.getShell(), "Atention", claerAllConfrimMsg);
		}
		return result;
	}

}
