package stackoverflow.ViewAndDialog;

import java.io.IOException;

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
import stackoverflow.LocalJsonConnector.SearchingHistory;
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
	public static final String ID = "stackoverflow.ViewAndDialog.SearchingHistoryView";

	@Inject
	IWorkbench workbench;
	SearchingHistory searchingHistory;
	private TableViewer viewer;
	private Action open;
	private Action delete;
	private Action refresh;
	private Action doubleClickAction;

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
		
		// Create table viewer
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION );
		createTable();
		
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "StackOverFlow.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	String[] text;
	String[] order;
	String[] sort;
	String[] site;
	String[] tagged;
	String[] date;

	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage activeEvent = win.getActivePage();
	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

	private void createTable() {
		Table table = this.viewer.getTable();
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
		try {
			searchingHistory = new SearchingHistory();
			int lenght = searchingHistory.getLenght();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void open() {
		String viewerID = "stackoverflow.ViewAndDialog.SearchResultView";
		int index = viewer.getTable().getSelectionIndex();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void delete() {
		int index = viewer.getTable().getSelectionIndex();
		if(searchingHistory.delete(index)) {
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

//	private void fillLocalPullDown(IMenuManager manager) {
//		manager.add(open);
//		manager.add(new Separator());
//		manager.add(delete);
//	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(open);
		manager.add(delete);
		manager.add(refresh);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

//	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(open);
//		manager.add(delete);
//	}

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
				createTable();
			}
		};
		refresh.setText("Refresh");
		refresh.setToolTipText("Refresh this Table");
		
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
