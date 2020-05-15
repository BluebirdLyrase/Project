package stackoverflow.handlers;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
public class Handler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String intitle;
		String title = "" ;
		String body = "";
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);		
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
			intitle = dialog.getSearchText();
		    System.out.println(dialog.getSearchText());
//			try {
//				SearchResult s = new SearchResult(intitle);
//				title = s.getTitle();
//				body = s.getBody();
//			} catch (IOException | JSONException e) {
//				e.printStackTrace();
//			}
//			System.out.println(title);
//			System.out.println(body);
			MessageDialog.openInformation(window.getShell(),title,body);
		    }
		return null;
	}
}
