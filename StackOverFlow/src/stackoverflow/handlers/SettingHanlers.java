package stackoverflow.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import stackoverflow.ViewAndDialog.SettingDialog;
public class SettingHanlers extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		SettingDialog  dialog = new SettingDialog(window.getShell());
		dialog.create();
		dialog.open();
		return null;
	}
}
