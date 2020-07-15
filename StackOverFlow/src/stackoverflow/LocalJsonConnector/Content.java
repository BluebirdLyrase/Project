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

	public Content() throws IOException, JSONException {
		LOGGER.setLevel(Level.ALL);
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
		boolean result = MessageDialog.openConfirm(win.getShell(), "Atention", "are you sure you want to delete this?");
		if (result) {
			try {
				File file = new File(fileDirURL + "\\" + filename);
				file.delete();
				new ContentTitleWriter().delete(index);
			} catch (IOException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Delete has been cancel by user.");
		}
		return result;
	}

}
