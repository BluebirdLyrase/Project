package stackoverflow.LocalJsonConnector;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

public class Log {
	
	protected static final Logger LOGGER = Logger.getLogger(Log.class.getName());

	private String defaultDir = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	protected String fileDirURL = (defaultDir + "\\StackOverFlowHelper\\LOG");
	private File fileDir = new File(fileDirURL);
	protected String arrayName = "items";
	
	public Log() {
		LOGGER.setLevel(Level.SEVERE);
		// Check if there is already Stackoverflow dir if not create one
		if (fileDir.mkdir()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory create" + fileDir.getName());
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "Directory already exists.");
		}
	}
	
	public void saveLog(Exception log) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		log.printStackTrace(pw);
		String logString = sw.toString();
		String time = LocalDateTime.now().toString();
		String filename = time.replace(":", "_").replace(".", "S");
		String filePath = fileDirURL + "\\" + filename + ".txt";
		
		try {
			File newFile = new File(filePath);
			if (newFile.createNewFile()) {
			Files.writeString(Paths.get(filePath), logString, StandardOpenOption.WRITE);
			}else {
				LOGGER.info("[" + LOGGER.getName() + "] " + "Connot Create File.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			new Log().saveLog(e);
		}
	}
	
	

}
