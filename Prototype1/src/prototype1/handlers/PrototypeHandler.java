package prototype1.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;
import org.eclipse.jface.dialogs.MessageDialog;
import prototype1.APIconnecter.PrototypeData;
import prototype1.APIconnecter.SearchResultList;;

public class PrototypeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException{
		
		try {
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView("prototype1.NewViews.NewViews");
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String title = "title";
//		String body = "body";
//		
//			try {
//				PrototypeData p = new PrototypeData("https://api.stackexchange.com/2.2/questions/60719951?order=desc&sort=activity&site=stackoverflow&filter=!9Z(-wwYGT");
//				title = p.getTitle();
//				body = p.getBody();
//			} catch (IOException | JSONException e) {
//				e.printStackTrace();
//			}
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(window.getShell(),title,body);
		
		
		
		return null;
		
	}



}
