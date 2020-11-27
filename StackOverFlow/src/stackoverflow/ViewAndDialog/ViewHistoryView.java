package stackoverflow.ViewAndDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.Window;
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
import stackoverflow.APIConnecter.StackExchangeConnecter;
import stackoverflow.LocalJsonConnector.ContentWriter;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.ViewHistoryList;
import stackoverflow.database.PinnedQuestionWriter;

public class ViewHistoryView extends ViewPart {

	private static final Logger LOGGER = Logger.getLogger(StackExchangeConnecter.class.getName());
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
	private Action search;
	private Action pinToTeam;
	private String[] id;
	private String[] title;
	private String[] tags;
	private String[] date;
	private String[] site;
	private int lenght;
	private Table table;
	private ViewHistoryList viewHistory;
	private boolean isCustom;

	// value form customTable use date ass a key
	private ArrayList<String> cdate = new ArrayList<String>();

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
//		contributeToActionBars();
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
		
		TableViewerColumn siteColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		siteColumn.getColumn().setWidth(100);
		siteColumn.getColumn().setText("Site");
		
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
			site = viewHistory.getSite();
			
			for (int i = 0; i < lenght; i++) {
				new TableItem(table, SWT.NONE).setText(new String[] { title[i], tags[i], date[i], id[i],site[i] });
			}

			isCustom = false;

		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void createCustomTableViewer() {
		ViewHistorySearchDialog dialog = new ViewHistorySearchDialog(win.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
			// clear all previous content
			table.removeAll();
			cdate.clear();
			for (int i = 0; i < lenght; i++) {
				if (isMatch(i, dialog.getSearchText(), dialog.getTagsText())) {
					cdate.add(date[i]); // add id as a key for right click function
					new TableItem(table, SWT.NONE).setText(new String[] { title[i], tags[i], date[i], id[i] });
				}
			}
			isCustom = true;
		}
	}

	private boolean isMatch(int index, String searchText, String tagsText) {
		boolean result = false;
		boolean simTitle = (title[index].toLowerCase().contains(searchText.toLowerCase()) || searchText == null);
		boolean simTags = (tags[index].toLowerCase().contains(tagsText.toLowerCase()) || tagsText == null);
		result = simTitle && simTags;
		return result;
	}

	private int getRealIndex() {
		int currentIndex = viewer.getTable().getSelectionIndex();
		int index = 0;
		if (isCustom) {
			for (int i = 0; i < lenght; i++) {
				if (cdate.get(currentIndex).equals(date[i])) { // matching cid to actual id to find original index in
																// array and table
					index = i;
					LOGGER.info("index = " + index + "||| title = " + title[index]);
					LOGGER.info("date : " + date[index] + "==" + "cdate" + cdate.get(currentIndex));
					break;
				}
			}
		} else {
			index = currentIndex;
			LOGGER.info("no custom table");
		}

		return index;
	}

	private void open() {
		int index = getRealIndex();
		String viewerID = "stackoverflow.ViewAndDialog.ContentView";

		// Random number to be an ID
		String secondaryId = Double.toString(Math.random());
		try {
			activeEvent.showView(viewerID, secondaryId, IWorkbenchPage.VIEW_ACTIVATE);
			IViewReference currentView = page.findViewReference(viewerID, secondaryId);
			IViewPart viewPart = currentView.getView(true);
			ContentView myView = (ContentView) viewPart;
			myView.setContent(id[index],site[index],title[index]);
		} catch (PartInitException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void delete() {
		int index = getRealIndex();
		viewHistory.delete(index);
			createTable();
		
	}

	private void saveOffline() {
		int index = getRealIndex();
		try {
			String msg = new ContentWriter().saveContent(
					// call AllContentObjectOnly() to create JSON Object
					new AllContentObjectOnly().getJsonObject(id[index],site[index]), id[index], title[index], site[index]);
			showMsg(msg);
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void saveFavorite() {
		int index = getRealIndex();
		try {
			String msg = new FavoriteWriter().saveFavorite(title[index], id[index], site[index]);
			showMsg(msg);
		} catch (IOException | JSONException e) {
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
		
		int index = viewer.getTable().getSelectionIndex();
		String msg = new PinnedQuestionWriter().pinnedWriter(id[index],site[index],title[index],pintext) ;
		showMsg(msg);
		pinDl.close();
	}
	
	private void showMsg(String msg) {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		MessageDialog.openInformation(win.getShell(), "Atention", msg);
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

//	private void contributeToActionBars() {
//		IActionBars bars = getViewSite().getActionBars();
//		fillLocalPullDown(bars.getMenuManager());
		// fillLocalToolBar(bars.getToolBarManager());
//	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(open);
		manager.add(delete);
		manager.add(refresh);
		manager.add(search);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(saveFavorite);
		manager.add(saveOffline);
		manager.add(pinToTeam);
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

		search = new Action() {
			public void run() {
				createCustomTableViewer();
			}
		};
		search.setText("Search");
		search.setToolTipText("Search from this table");

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
		pinToTeam = new Action() {
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
		pinToTeam.setText("Pin To Team");
		pinToTeam.setToolTipText("Pin this question to developing team");
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
