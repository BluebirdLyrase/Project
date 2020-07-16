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
import stackoverflow.LocalJsonConnector.SearchingWriter;
import stackoverflow.ViewAndDialog.SearchResultView;
import stackoverflow.ViewAndDialog.UserInputDialog;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

//////Project Eclipse-SOF v1.2.0

public class SearchHandler extends AbstractHandler {

	private String intitle;
	private String order;
	private String sort;
	private String site;
	private String tagged;
	private IWorkbenchWindow window;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.create();

		if (dialog.open() == Window.OK) {

			intitle = dialog.getSearchText();
			order = dialog.getOrder();
			sort = dialog.getSort();
			site = dialog.getSite();
			tagged = dialog.getTagsText();
			
			boolean intitleIsValid = intitle.matches("[\\p{Graph}\\p{Space}]+") || intitle.isEmpty();
			boolean taggedIsValid = tagged.matches("[\\p{Graph}\\p{Space}]+") || tagged.isEmpty();
			boolean isEnglish = intitleIsValid && taggedIsValid ;
			if (isEnglish) {
				createSearchResult();
			} else {
				MessageDialog.openError(window.getShell(), "Error",
						"not found the result you are searching please dont type other language");
			}

		}

		return null;
	}

	private void createSearchResult() {
		try {
			String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";
			SearchResult searchResult;
			searchResult = new SearchResult(intitle, 1, 40, order, sort, site, tagged);
			new SearchingWriter().saveSearchTextHistory(intitle, order, sort, site, tagged);

			if (searchResult.haveResult()) {

				String[] titleList = searchResult.getTitleList();
				String[] questionIdList = searchResult.getQuestionIdList();

				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				window.getActivePage().showView(viewerID);

				IViewPart viewPart = page.findView(viewerID);

				SearchResultView myView = (SearchResultView) viewPart;

				myView.setSearchResult(titleList, questionIdList);

			} else {
				MessageDialog.openError(window.getShell(), "Error", "not found the result you are searching");
			}
		} catch (IOException | JSONException | PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
