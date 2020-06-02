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

public class SearchHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String intitle;
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.create();
		
		if (dialog.open() == Window.OK) {
			
			intitle = dialog.getSearchText();
			System.out.println("intitle = " + intitle);
			SearchResult searchResult;
			
			try {
				HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView("stackoverflow.ViewAndDialog.SearchResultView");
				searchResult = new SearchResult("Eclipse");
				String[] titleList = searchResult.getTitleList();
				String[] questionIdList = searchResult.getQuestionIdList();
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IViewPart viewPart = page.findView("stackoverflow.ViewAndDialog.SearchResultView");
				SearchResultView myView = (SearchResultView) viewPart;
				myView.setSearchResult(titleList,questionIdList);
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
//			MessageDialog.openError(window.getShell(), "Error", "not found the result you are searching");
		}

		return null;
	}
}
