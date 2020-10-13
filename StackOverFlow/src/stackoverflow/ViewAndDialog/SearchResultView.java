package stackoverflow.ViewAndDialog;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.ViewPart;
import org.json.JSONException;
import org.osgi.framework.Bundle;

import stackoverflow.APIConnecter.AllContentObjectOnly;
import stackoverflow.LocalJsonConnector.ContentWriter;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.database.PinnedQuestionWriter;

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

public class SearchResultView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.SearchResultView";

	@Inject
	IWorkbench workbench;
	private String[] id;
	private String site;
	private TableViewer viewer;
	private Action doubleClickAction;
	private Action saveFavorite;
	private Action saveOffline;
	private Action open;
	private Action pinToTeam;
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

			String path = "\\images\\stackoverflow.png";
			Bundle bundle = Platform.getBundle("StackOverFlow");
			URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(path), null);
			URL fileURL = null;
			try {
				fileURL = FileLocator.toFileURL(url);
			} catch (IOException e) {
				new Log().saveLog(e);
				e.printStackTrace();
			}
			ImageDescriptor imageDesc = ImageDescriptor.createFromURL(fileURL);
			Image image = imageDesc.createImage();

			return image;
		}
	}

	String[] titleList;
	String[] questionIdList;
	IWorkbench wb = PlatformUI.getWorkbench();
	IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
	IWorkbenchPage activeEvent = win.getActivePage();
	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

	public void setSearchResult(String[] titleList, String[] questionIdList, String site) {
		this.titleList = titleList;
		this.questionIdList = questionIdList;
		this.site = site;
		/// setData to next result page
		id = new String[questionIdList.length];
		for (int i = 0; i < questionIdList.length; i++) {
			id[i] = questionIdList[i];
		}
		ColumnViewerToolTipSupport.enableFor(viewer);
		viewer.setInput(titleList);
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(null);
		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "StackOverFlow.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
//		contributeToActionBars();
	}

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
			myView.setContent(id[index],site);
		} catch (PartInitException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void saveOffline() {
		int index = viewer.getTable().getSelectionIndex();
		try {
			String msg = new ContentWriter().saveContent(new AllContentObjectOnly().getJsonObject(id[index],site), id[index], titleList[index],site);
			showMsg(msg);
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}

	private void saveFavorite() {
		int index = viewer.getTable().getSelectionIndex();
		try {
			String msg = new FavoriteWriter().saveFavorite(titleList[index], id[index], site);
			showMsg(msg);
		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	private void pinToTeam() throws IOException, JSONException {
		PinTextInputDialog  PinDl = new PinTextInputDialog(win.getShell());
		PinDl.createDialogArea(win.getShell());
		PinDl.close();
		int index = viewer.getTable().getSelectionIndex();
		String msg = new PinnedQuestionWriter().pinnedWriter(id[index],site,titleList[index],PinDl.getPinText()) ;
		showMsg(msg);
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
				SearchResultView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

//	private void contributeToActionBars() {
//		IActionBars bars = getViewSite().getActionBars();
	//	fillLocalPullDown(bars.getMenuManager());
//		fillLocalToolBar(bars.getToolBarManager());
//	}


	private void fillContextMenu(IMenuManager manager) {
		// Other plug-ins can contribute there actions here
		manager.add(open);
		manager.add(new Separator());
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		manager.add(saveFavorite);
		manager.add(saveOffline);
		manager.add(pinToTeam);
	}

	private void makeActions() {

		saveFavorite = new Action() {
			public void run() {
				saveFavorite();
			}
		};

		open = new Action() {
			public void run() {
				open();
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this question on new tab");
//		open.setImageDescriptor(
//				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		saveFavorite.setText("save to favorite");
		saveFavorite.setToolTipText("save this question to favorite list");
//		saveFavorite.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		saveOffline = new Action() {
			public void run() {
				saveOffline();
			}
		};
		saveOffline.setText("save to Offline");
		saveOffline.setToolTipText("save this question to Offline list");
//		saveOffline.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
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
