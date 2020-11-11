package stackoverflow.ViewAndDialog;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContentObjectOnly;
import stackoverflow.LocalJsonConnector.ContentWriter;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.database.PinnedQuestionWriter;

import java.io.IOException;
import javax.inject.Inject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;

public class ContentView extends ViewPart {

	@Inject
	IWorkbench workbench;

	Composite parent;
	public static final String ID = "stackoverflow.ViewAndDialog.ContentView";

	private String id = null;
	private String qtitle = "";
	private String HTMLtext;
	private boolean isOffline = false;
	private Action fav;
	private Action off;
	private Action home;
	private Action pin;
	private Browser browser;
	private String site;
	
	public void setContent(String id,String site) {
		this.id = id;
		this.site = site;
		parent.layout(true, true);
		final Point newSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		parent.setSize(newSize);

		Composite contentView;
		contentView = new Composite(parent, SWT.None);
		contentView.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		contentView.setLayout(new GridLayout(1, true));

		try {
			browser = new Browser(contentView, SWT.NONE);
			browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		HTMLBuilder html = new HTMLBuilder(this.id, this.isOffline,this.site);
		qtitle = html.getTitle();
		HTMLtext = html.getHtml();
		browser.setText(HTMLtext);

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
//		fillLocalPullDown(bars.getMenuManager());
		IActionBars bars = getViewSite().getActionBars();
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void saveOffline() {
		try {
			String msg = new ContentWriter().saveContent(new AllContentObjectOnly().getJsonObject(id,site), id, qtitle,site);
			showMsg(msg);
		} catch (IOException|JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	private void pinToTeam() throws IOException, JSONException {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		PinTextInputDialog  pinDl = new PinTextInputDialog(win.getShell());
		pinDl.createDialogArea(win.getShell());
		pinDl.open();
		String pintext  = pinDl.getPinText();
		System.out.print("Pintext  : "+pintext);
		
		String msg = new PinnedQuestionWriter().pinnedWriter(id,site,qtitle,pintext) ;
		showMsg(msg);
		pinDl.close();
	}

	private void saveFavorite() {
		try {
			String msg = new FavoriteWriter().saveFavorite(qtitle, id,site);
			showMsg(msg);
		} catch (IOException|JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	
	private void showMsg(String msg) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		MessageDialog.openInformation(win.getShell(), "Atention", msg);
	}

	private void backToHome() {
		browser.setText(HTMLtext);
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(off);
		manager.add(fav);
		manager.add(home);
		manager.add(pin);
	}

	private void makeActions() {
		off = new Action() {
			public void run() {
				saveOffline();
			}
		};
		off.setText("Save to Offline");
		off.setToolTipText("Save this question to your Offline");

		fav = new Action() {
			public void run() {
				saveFavorite();

			}
		};
		fav.setText("Save to Favorite");
		fav.setToolTipText("Save this question to Favorite");

		home = new Action() {
			public void run() {
				backToHome();
			}
		};
		home.setText("Home");
		home.setToolTipText("Back to Stack Overflow Content");
		
		pin = new Action() {
			public void run() {
				try {
					pinToTeam();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		pin.setText("Pin To Team");
		pin.setToolTipText("Pin this question to developing team");
		

	}

}
