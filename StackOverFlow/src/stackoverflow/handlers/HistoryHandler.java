package stackoverflow.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import stackoverflow.LocalJsonConnector.Log;


public class HistoryHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {
			String viewerID1 = "stackoverflow.ViewAndDialog.SearchingHistoryView";
			String viewerID2 = "stackoverflow.ViewAndDialog.ViewHistoryView";
			window.getActivePage().showView(viewerID1);
			window.getActivePage().showView(viewerID2);
		} catch (PartInitException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error", "There is a problem occur. please email us your Log folder" );
		}
			return null;
		
	}
}
