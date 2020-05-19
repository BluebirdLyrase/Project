package stackoverflow.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;

import stackoverflow.APIConnecter.QuickSearchResult;
import stackoverflow.APIConnecter.Teststub;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

public class Handler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String intitle;
		String title = "";
		String body = "";
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
			intitle = dialog.getSearchText();
			System.out.println(dialog.getSearchText());

			QuickSearchResult qSearchResult;
//			Teststub qSearchResult;
//			qSearchResult = new Teststub(intitle);
			
			try {
				qSearchResult = new QuickSearchResult(intitle);
				body = qSearchResult.getBody();
				title = qSearchResult.getTitle();
				SearchResultDialog searchResult = new SearchResultDialog(window.getShell(), title, body);
				searchResult.open();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
//			body = qSearchResult.getBody();
//			title = qSearchResult.getTitle();
//			SearchResultDialog searchResult = new SearchResultDialog(window.getShell(), title, body);
//			searchResult.open();

			// MessageDialog.openInformation(window.getShell(),"This should be the
			// title","this should be the body");

		}
		return null;
	}
}
