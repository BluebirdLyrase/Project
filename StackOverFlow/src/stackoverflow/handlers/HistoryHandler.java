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

import stackoverflow.ViewAndDialog.ViewHistoryView;


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
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
			return null;
		
	}
}
