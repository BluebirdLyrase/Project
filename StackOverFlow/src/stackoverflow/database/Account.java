package stackoverflow.database;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import stackoverflow.LocalJsonConnector.LocalJsonList;

public class Account extends LocalJsonList {
	
	public Account() {
		super("Account");
	}

	private boolean loggedIn = false;
	private boolean result = false;

	public boolean Logout() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		boolean result = true;
		result = MessageDialog.openConfirm(win.getShell(), "Atention", "Are you sure you want to Log-Out?");
		if (result) {
			deleteFile(filePath);
			MessageDialog.openInformation(win.getShell(), "Atention", "Log-Out Successfully");
		}
		return result;
	}
	
	public boolean Loggin(String username,String password){
		
		return result;
	}
	
	private void setDatabase() {
		
	}
	
	public boolean isLoggedIn() {
		if(true) {
			loggedIn = false;
		}
		else {
			setDatabase();
			loggedIn = true;
		}
		return loggedIn;
	}
	
}
