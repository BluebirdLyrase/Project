package stackoverflow.ViewAndDialog;
import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContentObjectOnly;
import stackoverflow.LocalJsonConnector.ContentWriter;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

public abstract class SOFViewPart extends ViewPart {
	protected IWorkbench workbench;
	protected TableViewer viewer;
	protected Table table;
	protected Action open;
	protected Action delete;
	protected Action saveFavorite;
	protected Action saveOffline;
	protected Action refresh;
	protected Action doubleClickAction;
	
	protected IWorkbench wb = PlatformUI.getWorkbench();
	protected IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	protected IWorkbenchPage activeEvent = win.getActivePage();
	protected IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

	protected String[] id;
	protected String[] title;
	
	@Override
	public abstract void createPartControl(Composite arg0);

	@Override
	public abstract void setFocus() ;
	
	protected abstract void delete();
	protected abstract void fillContextMenu(IMenuManager manager);
	protected abstract void createTable();
	protected abstract void createTableViewer();
	
	////make Action Class
	protected void open() {
		int index = viewer.getTable().getSelectionIndex();
		String viewerID = "stackoverflow.ViewAndDialog.ContentView";

		// Random number to be an ID
		String secondaryId = Double.toString(Math.random());
		try {
			activeEvent.showView(viewerID, secondaryId, IWorkbenchPage.VIEW_ACTIVATE);
			IViewReference currentView = page.findViewReference(viewerID, secondaryId);
			IViewPart viewPart = currentView.getView(true);
			ContentView myView = (ContentView) viewPart; 
			myView.setContent(id[index]);
		} catch (PartInitException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	
	protected void saveOffline(){
		int index = viewer.getTable().getSelectionIndex();
		try {
			new ContentWriter().saveContent(
					//call AllContentObjectOnly() to create JSON Object
					new AllContentObjectOnly().getJsonObject(id[index]), 
					id[index], title[index]);
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	
	protected void saveFavorite(){
		int index = viewer.getTable().getSelectionIndex();
		try {
			new FavoriteWriter().saveFavorite(title[index], id[index]);
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	
	////make Action in context menu
	protected void makeActions() {
		open = new Action() {
			public void run() {
				open();
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this question on new tab");

		delete = new Action() {
			public void run() {
				delete();
			}
		};
		delete.setText("Delete");
		delete.setToolTipText("delete this record");
	
		saveFavorite = new Action() {
			public void run() {
				saveFavorite();
			}
		};
		saveFavorite.setText("save to favorite");
		saveFavorite.setToolTipText("save this question to favorite list");
	
		refresh = new Action() {
			public void run() {
				createTableViewer();
			}
		};
		refresh.setText("Refresh");
		refresh.setToolTipText("Refresh this table");
		
		saveOffline = new Action() {
			public void run() {
				saveOffline();
			}
		};
		saveOffline.setText("save to Offline");
		saveOffline.setToolTipText("save this question to Offline list");

		doubleClickAction = new Action() {
			public void run() {
				open();
			}
		};
	}
	
	protected void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	protected void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
	}
	

}
