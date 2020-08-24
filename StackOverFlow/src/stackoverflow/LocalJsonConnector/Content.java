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
		LOGGER.setLevel(Level.SEVERE);
		// Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("Directory create" + fileDir.getName());
		} else {
			LOGGER.info("Directory already exists.");
		}
	}

	public void delete(String filename, int index) {
			try {
				deleteFile(fileDirURL + "\\" + filename);
				new ContentTitleWriter().delete(index);
			} catch (IOException | JSONException e) {
				e.printStackTrace();
				new Log().saveLog(e);
			}
	}

	public void clear() {
		try {

			String[] entries = fileDir.list();
			for (String s : entries) {
				deleteFile(fileDirURL + "\\" + s);
			}
			new ContentTitleWriter().clear();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}
	}

	public void clearAll() {

		// Clear Every List
		File otherfileDir = new File(defaultDir + "\\StackOverFlowHelper");
		String[] otherEntries = otherfileDir.list();
		for (String s : otherEntries) {
			deleteFile(otherfileDir.getPath() + "\\" + s);
		}
		otherfileDir.delete();
		/// Clear Offline Content
		String[] entries = fileDir.list();
		if (entries != null) {
			for (String s : entries) {
				deleteFile(fileDirURL + "\\" + s);
			}
		}
		fileDir.delete();

	}

	public double getSize() {
		double size = 0.0;
		try {
			double listSize = new ContentTitleList().getSize();
			double ContentSize = super.getSize(fileDirURL);
			size = listSize + ContentSize;
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}
		return size;
	}

}
