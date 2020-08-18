package stackoverflow.ViewAndDialog;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.APIConnecter.AllContentObjectOnly;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Comment;
import stackoverflow.DataClass.Question;
import stackoverflow.LocalJsonConnector.ContentWriter;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.ViewWriter;

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
	boolean isOffline= false;
	private Action fav;
	private Action off;
	Browser browser;

	public void setContent(String id) {
		this.id = id;
		parent.layout(true, true);
		final Point newSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		parent.setSize(newSize);

		Composite contentViwew;
		contentViwew = new Composite(parent, SWT.None);
		contentViwew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		contentViwew.setLayout(new GridLayout(1, true));

		Browser browser;

		try {
			browser = new Browser(contentViwew, SWT.NONE);
			browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		HTMLBuilder html = new HTMLBuilder(this.id,this.isOffline);
		qtitle = html.getTitle();
		browser.setText(html.getHtml());

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

	public void saveOffline() {
		try {
			new ContentWriter().saveContent(new AllContentObjectOnly().getJsonObject(id), id, qtitle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveFavorite() {
		try {
			new FavoriteWriter().saveFavorite(qtitle, id);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fillLocalToolBar(IToolBarManager manager) {
			manager.add(off);
			manager.add(fav);
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
				try {
					FavoriteWriter favWriter = new FavoriteWriter();
					favWriter.saveFavorite(qtitle, id);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		fav.setText("Save to Favorite");
		fav.setToolTipText("Save this question to Favorite");
	}

}
