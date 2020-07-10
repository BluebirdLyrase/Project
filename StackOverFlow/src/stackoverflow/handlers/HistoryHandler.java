package stackoverflow.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;

import stackoverflow.APIConnecter.QuickSearchResult;
import stackoverflow.ViewAndDialog.QuickSearchResultDialog;
import stackoverflow.ViewAndDialog.UserInputDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

public class HistoryHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		try {
			String viewerID = "stackoverflow.ViewAndDialog.SearchingHistoryView";
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(viewerID);
		} catch (PartInitException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		
	}
}
