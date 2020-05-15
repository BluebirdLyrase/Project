package testinput.handlers;



import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
public class SampleHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		UserInputDialog dialog = new UserInputDialog(window.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
		    System.out.println(dialog.getSearchText());
		    System.out.println(dialog.getTagText());
		}
		return null;
	}
}
