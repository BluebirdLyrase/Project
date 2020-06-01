package prototype1.handlers;


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
import org.eclipse.ui.part.ViewPart;
import org.json.JSONException;

import prototype1.APIconnecter.SearchResultList;
import prototype1.NewViews.TableView;

import org.eclipse.jface.dialogs.MessageDialog;
public class NewViewHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
	
				SearchResultList searchResultList;
				try {
				searchResultList = new SearchResultList("Eclipse");
				String[] titleList = searchResultList.getTitleList();
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IViewPart viewPart = page.findView("prototype1.NewViews.TableView");
				TableView myView = (TableView) viewPart;
				myView.setTitlelist(titleList);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		return null;
	}
}
