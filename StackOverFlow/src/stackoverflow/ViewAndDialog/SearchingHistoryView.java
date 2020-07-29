package stackoverflow.ViewAndDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.json.JSONException;

import stackoverflow.APIConnecter.SearchResult;
import stackoverflow.APIConnecter.StackOverFlowConnecter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.SearchingHistoryList;
import stackoverflow.LocalJsonConnector.SearchingWriter;

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

public class SearchingHistoryView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	private static final Logger LOGGER = Logger.getLogger(StackOverFlowConnecter.class.getName());
	public static final String ID = "stackoverflow.ViewAndDialog.SearchingHistoryView";

	@Inject
	IWorkbench workbench;
	private TableViewer viewer;
	private Action open;
	private Action delete;
	private Action refresh;
	private Action doubleClickAction;
	private Action search;
	private Table table;
	private SearchingHistoryList searchingHistory;

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
		LOGGER.setLevel(Level.INFO);
		// Create table viewer
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

	private String[] text;
	private String[] order;
	private String[] sort;
	private String[] site;
	private String[] tagged;
	private String[] date;
	private int lenght;

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

		TableViewerColumn searchTextColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		searchTextColumn.getColumn().setWidth(500);
		searchTextColumn.getColumn().setText("Search Text");

		TableViewerColumn orderColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		orderColumn.getColumn().setWidth(80);
		orderColumn.getColumn().setText("Order");

		TableViewerColumn sortColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		sortColumn.getColumn().setWidth(100);
		sortColumn.getColumn().setText("Sort By");

		TableViewerColumn siteColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		siteColumn.getColumn().setWidth(130);
		siteColumn.getColumn().setText("Site");

		TableViewerColumn tagsColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		tagsColumn.getColumn().setWidth(200);
		tagsColumn.getColumn().setText("Tagged");

		TableViewerColumn dateTimeColumn = new TableViewerColumn(this.viewer, SWT.CENTER);
		dateTimeColumn.getColumn().setWidth(300);
		dateTimeColumn.getColumn().setText("Date : Time");

		createTableViewer();

	}

	private void createTableViewer() {
		table.removeAll();
		try {
			searchingHistory = new SearchingHistoryList();
			lenght = searchingHistory.getLenght();
			text = searchingHistory.getSearchText();
			order = searchingHistory.getOrder();
			sort = searchingHistory.getSort();
			site = searchingHistory.getSite();
			tagged = searchingHistory.getTagged();
			date = searchingHistory.getSearchingDate();

			for (int i = 0; i < lenght; i++) {
				viewer.setData("text" + i, text[i]);
				new TableItem(table, SWT.NONE)
						.setText(new String[] { text[i], order[i], sort[i], site[i], tagged[i], date[i] });
			}
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void createCustomTableViewer() {
		SearchHistorySearchDialog dialog = new SearchHistorySearchDialog(win.getShell());
		dialog.create();
		if (dialog.open() == Window.OK) {
			// clear all previous content
			table.removeAll();
			cdate.clear();
			for (int i = 0; i < lenght; i++) {
				if (isMatch(i, dialog.getSearchText(),dialog.getOrder(),dialog.getSort(),dialog.getSite(),dialog.getTagsText())) {
					cdate.add(date[i]); // add id as a key for right click function
					new TableItem(table, SWT.NONE)
							.setText(new String[] { text[i], order[i], sort[i], site[i], tagged[i], date[i] });
				}
			}
			isCustom = true;
		}
	}

	private boolean isMatch(int index, String searchText,String order, String sort, String site, String tagged) {
		boolean result = false;
		boolean simSearch = (text[index].toLowerCase().contains(searchText.toLowerCase()) || searchText == null);
		boolean simOrder = (this.order[index].equals(order) || order.equals("any")) ;
		boolean simSort = (this.sort[index].equals(sort) || sort.equals("any"));
		boolean simSite = (this.site[index].equals(site) || site.equals("any"));
		boolean simTags = (this.tagged[index].toLowerCase().contains(tagged.toLowerCase()) || tagged == null);
		result = simSearch && simOrder && simSort && simSite && simTags ;
		LOGGER.info("match data searchtext : "+text[index]+"| date : "+date[index]);
		return result;
	}
	
	private int getRealIndex() {
		int currentIndex = viewer.getTable().getSelectionIndex();
		int index = 0;
		if (isCustom) {
			for (int i = 0; i < lenght; i++) {
				if (cdate.get(currentIndex).equals(date[i])) { //matching cid to actual id to find original index in array and table
					index = i;
					LOGGER.info("index = "+index+"||| title = "+ text[index]);
					LOGGER.info("date : "+date[index]+"=="+"cdate"+cdate.get(currentIndex));
					break;
				}
			}
		}else {
			index = currentIndex;
			LOGGER.info("no custom table");
		}
		
		return index;
	}

	private void open() {
		String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";
		int index = getRealIndex();
		String intitle = this.text[index];
		String order = this.order[index];
		String sort = this.sort[index];
		String site = this.site[index];
		String tagged = this.tagged[index];
		SearchResult searchResult;

		try {

			searchResult = new SearchResult(intitle, 1, 40, order, sort, site, tagged);
			new SearchingWriter().saveSearchTextHistory(intitle, order, sort, site, tagged);

			if (searchResult.haveResult()) {

				String[] titleList = searchResult.getTitleList();
				String[] questionIdList = searchResult.getQuestionIdList();

				win.getActivePage().showView(viewerID);

				IViewPart viewPart = page.findView(viewerID);

				SearchResultView myView = (SearchResultView) viewPart;

				myView.setSearchResult(titleList, questionIdList);

			} else {
				MessageDialog.openError(win.getShell(), "Error", "not found the result you are searching");
			}
		} catch (IOException | JSONException | PartInitException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}

	}

	private void delete() {
		int index = getRealIndex();
		if (searchingHistory.delete(index)) {
			createTable();
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				SearchingHistoryView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
//		fillLocalPullDown(bars.getMenuManager());
//		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(open);
		manager.add(delete);
		manager.add(refresh);
		manager.add(search);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void makeActions() {
		open = new Action() {
			public void run() {
				open();
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this question on new tab");
		open.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		delete = new Action() {
			public void run() {
				delete();
			}
		};
		delete.setText("Delete");
		delete.setToolTipText("delete this record");
		delete.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		refresh = new Action() {
			public void run() {
				createTableViewer();
			}
		};
		refresh.setText("Refresh");
		refresh.setToolTipText("Refresh this Table");
		
		search = new Action() {
			public void run() {
				createCustomTableViewer();
			}
		};
		search.setText("Search");
		search.setToolTipText("Search from this table");
		
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
