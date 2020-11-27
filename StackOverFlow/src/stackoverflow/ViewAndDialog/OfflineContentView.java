package stackoverflow.ViewAndDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Log;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.jface.action.*;
import org.eclipse.ui.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;

import java.io.IOException;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class OfflineContentView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.OfflineContentView";
	private Composite parent;
	private String id = null;
	private String site;
	private String HTMLtext;
	private boolean isOffline = true;
	private Browser browser;
	private Action home;

	public void setContent(String id,String site) {
		this.id = id;
		this.site = site;
		parent.layout(true, true);
		final Point newSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		parent.setSize(newSize);

		Composite contentViwew;
		contentViwew = new Composite(parent, SWT.None);
		contentViwew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		contentViwew.setLayout(new GridLayout(1, true));

		try {
			browser = new Browser(contentViwew, SWT.NONE);
			browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		try {
		HTMLBuilder html = new HTMLBuilder(this.id, this.isOffline,this.site);
		HTMLtext = html.getHtml();
		browser.setText(HTMLtext);
		} catch (IOException | JSONException e) {
			browser.setText("Error : No internet connection");
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
		makeActions();
		contributeToActionBars();
	}

	@Override
	public void setFocus() {
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	private void backToHome() {
		browser.setText(HTMLtext);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(home);
	}

	private void makeActions() {

		home = new Action() {
			public void run() {
				backToHome();
			}
		};
		home.setText("Home");
		home.setToolTipText("Back to Stack Overflow Content");

	}

}
