package stackoverflow.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;

import stackoverflow.APIConnecter.QuickSearchResult;
import stackoverflow.ViewAndDialog.QuickSearchResultDialog;
import stackoverflow.ViewAndDialog.SearchingHistoryView;
import stackoverflow.ViewAndDialog.UserInputDialog;
import stackoverflow.ViewAndDialog.ViewHistoryView;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

public class HistoryHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		try {
			String viewerID1 = "stackoverflow.ViewAndDialog.SearchingHistoryView";
			String viewerID2 = "stackoverflow.ViewAndDialog.ViewHistoryView";
			
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			
			window.getActivePage().showView(viewerID1);
//			IViewPart viewPart1 = page.findView(viewerID1);
//			SearchingHistoryView myView1 = (SearchingHistoryView) viewPart1;
//			myView1.setEvent(event);
			
			window.getActivePage().showView(viewerID2);
//			IViewPart viewPart2 = page.findView(viewerID2);
//			ViewHistoryView myView2 = (ViewHistoryView) viewPart2;
//			myView2.setEvent(event);
			
			
		} catch (PartInitException e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		
	}
}
