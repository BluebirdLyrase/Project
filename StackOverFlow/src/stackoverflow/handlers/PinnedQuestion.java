package stackoverflow.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.database.Account;

import org.eclipse.jface.dialogs.MessageDialog;

public class PinnedQuestion extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Account account = new Account();
		try {
			
		if(account.isLoggedIn()) {
		String viewerID = "stackoverflow.ViewAndDialog.PinnedQuestionView";
		window.getActivePage().showView(viewerID);
		}else {
			MessageDialog.openInformation(window.getShell(), "Attention!", "This feature only availible while logged in server" );
		}
		
		} catch (PartInitException | JSONException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error", "There is a problem occur. please email us your Log folder" );
		}
		return null;
	}
}
