package stackoverflow.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import stackoverflow.LocalJsonConnector.Log;

import org.eclipse.jface.dialogs.MessageDialog;

public class PinnedQuestion extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		String viewerID = "stackoverflow.ViewAndDialog.PinnedQuestionView";
		try {
			window.getActivePage().showView(viewerID);
		} catch (PartInitException e) {
			e.printStackTrace();
			new Log().saveLog(e);
			MessageDialog.openError(window.getShell(), "Error", "There is problem occur on StackOverFlow Helper plug-in. please email us your Log folder" );
		}
		return null;
	}
}
