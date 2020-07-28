package stackoverflow.ViewAndDialog;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContentObjectOnly;
import stackoverflow.LocalJsonConnector.ContentWriter;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.ViewHistoryList;

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

public class ViewHistoryView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.ViewHistoryView";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private Action open;
	private Action delete;
	private Action saveFavorite;
	private Action saveOffline;
	private Action refresh;
	private Action doubleClickAction;
	private String[] id;
	private String[] title;
	private String[] tags;
	private String[] date;
	private int lenght;
	private Table table;
	private ViewHistoryList viewHistory;
	private boolean isCustom;
	
	//value form customTable
	private ArrayList<String> cid = new ArrayList<String>();

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		@Override
		public Image getImage(Object obj) {
			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	@Override
	public void createPartControl(Composite parent) {

		this.viewer = new TableViewer(parent,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		createTable();
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "StackOverFlow.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private IWorkbench wb = PlatformUI.getWorkbench();
	private IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	private IWorkbenchPage activeEvent = win.getActivePage();
	private IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

	private void createTable() {

		table = this.viewer.getTable();
		table.removeAll();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setVisible(true);

		TableViewerColumn titleColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		titleColumn.getColumn().setWidth(500);
		titleColumn.getColumn().setText("Title");

		TableViewerColumn tagsColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		tagsColumn.getColumn().setWidth(200);
		tagsColumn.getColumn().setText("Tagged");

		TableViewerColumn dateTimeColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		dateTimeColumn.getColumn().setWidth(300);
		dateTimeColumn.getColumn().setText("Date : Time");

		TableViewerColumn idColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		idColumn.getColumn().setWidth(100);
		idColumn.getColumn().setText("ID");
		createTableViewer();

	}
	
	private void createTableViewer() {
		table.removeAll();
		try {
			viewHistory = new ViewHistoryList();
			lenght = viewHistory.getLenght();
			title = viewHistory.getTitle();
			tags = viewHistory.getTags();
			date = viewHistory.getViewDate();
			id = viewHistory.getId();

			for (int i = 0; i < lenght; i++) {
				new TableItem(table, SWT.NONE).setText(new String[] { title[i], tags[i], date[i], id[i] });
			}
			
//			isCustom = false;
			
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	
//	private void createCustomTableViewer() {
//		
//			for (int i = 0; i < lenght; i++) {
//				if() { 
//					//TODO 
//				cid.add(id[i]); // add id as a key for right click function
//				new TableItem(table, SWT.NONE).setText(new String[] { title[i], tags[i], date[i], id[i] });
//				}
//			}
//			isCustom = true;
//	}
//	
//	private int getRealIndex(String cid) {
//		int realIndex = 0;
//		for (int i = 0; i < lenght; i++) {
//			if(cid.equals(id[i])) {
//				realIndex = i;
//				break;
//			}
//		}
//		return realIndex;
//	}

	
	private void open() {
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

	private void delete() {
		int index = viewer.getTable().getSelectionIndex();
		if(viewHistory.delete(index)) {
		createTable();
		}
	}
	
	private void saveOffline(){
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
	
	private void saveFavorite(){
		int index = viewer.getTable().getSelectionIndex();
		try {
			new FavoriteWriter().saveFavorite(title[index], id[index]);
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ViewHistoryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
//		fillLocalPullDown(bars.getMenuManager());
	//	fillLocalToolBar(bars.getToolBarManager());
	}


	private void fillContextMenu(IMenuManager manager) {
		manager.add(open);
		manager.add(delete);
		manager.add(refresh);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(saveFavorite);
		manager.add(saveOffline);
	}

	private void makeActions() {
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

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
