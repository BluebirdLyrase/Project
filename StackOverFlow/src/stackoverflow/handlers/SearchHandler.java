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
import stackoverflow.APIConnecter.SearchResult;
import stackoverflow.ViewAndDialog.SearchResultView;
import stackoverflow.ViewAndDialog.UserInputDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

//////Project Eclipse-SOF v1.0.1

public class SearchHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String intitle;
		String order;
		String sort;
		String site;
		String tagged;
		boolean acceptedOnly;
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.create();

		if (dialog.open() == Window.OK) {

			intitle = dialog.getSearchText();
			order = dialog.getOrder();
			sort = dialog.getSort();
			site = dialog.getSite();
			acceptedOnly = dialog.isAcceptedOnly();
			SearchResult searchResult;
			String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";

			try {

				searchResult = new SearchResult(intitle,1,40,order,sort,site);
				if (searchResult.haveResult()) {

					String[] titleList = searchResult.getTitleList();
					String[] questionIdList = searchResult.getQuestionIdList();

					IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					window.getActivePage().showView(viewerID);

					IViewPart viewPart = page.findView(viewerID);
					
					SearchResultView myView = (SearchResultView) viewPart;

					myView.setSearchResult(acceptedOnly,titleList, questionIdList, event);

				} else {
					MessageDialog.openError(window.getShell(), "Error", "not found the result you are searching");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PartInitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;
	}
}
