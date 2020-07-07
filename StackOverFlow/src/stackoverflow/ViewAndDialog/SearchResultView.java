package stackoverflow.ViewAndDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.osgi.framework.Bundle;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.*;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import javax.inject.Inject;

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
	private TableViewer viewer;
//	private Action action1;
//	private Action action2;
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

	            String path = "\\images\\stackoverflow.png";
	            Bundle bundle = Platform.getBundle("StackOverFlow");
	            URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path(path), null);
	            URL fileURL = null ;
	            try {
					 fileURL = FileLocator.toFileURL(url);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            ImageDescriptor imageDesc = ImageDescriptor.createFromURL(fileURL);
	            Image image = imageDesc.createImage();
				
	
	            return image;
//			return workbench.getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	String[] titleList;
	String[] questionIdList;
	IWorkbenchPage activeEvent;
	IWorkbenchPage page;

	public void setSearchResult(String[] titleList, String[] questionIdList,
			ExecutionEvent event) {
		this.titleList = titleList;
		this.questionIdList = questionIdList;
		
		activeEvent = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();	
		
		/// setData to next result page
		for (int i = 0; i < questionIdList.length; i++) {
			viewer.setData("questionId" + i, questionIdList[i]);
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
//		hookContextMenu();
		hookDoubleClickAction();
//		contributeToActionBars();
	}

//	private void hookContextMenu() {
//		MenuManager menuMgr = new MenuManager("#PopupMenu");
//		menuMgr.setRemoveAllWhenShown(true);
//		menuMgr.addMenuListener(new IMenuListener() {
//			public void menuAboutToShow(IMenuManager manager) {
//				SearchResultView.this.fillContextMenu(manager);
//			}
//		});
//		Menu menu = menuMgr.createContextMenu(viewer.getControl());
//		viewer.getControl().setMenu(menu);
//		getSite().registerContextMenu(menuMgr, viewer);
//	}
//
//	private void contributeToActionBars() {
//		IActionBars bars = getViewSite().getActionBars();
//		fillLocalPullDown(bars.getMenuManager());
//		fillLocalToolBar(bars.getToolBarManager());
//	}

//	private void fillLocalPullDown(IMenuManager manager) {
//		manager.add(action1);
//		manager.add(new Separator());
//		manager.add(action2);
//	}
//
//	private void fillContextMenu(IMenuManager manager) {
//		manager.add(action1);
//		manager.add(action2);
//		// Other plug-ins can contribute there actions here
//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
//	}
//
//	private void fillLocalToolBar(IToolBarManager manager) {
//		manager.add(action1);
//		manager.add(action2);
//	}
//
	private void makeActions() {
//		action1 = new Action() {
//			public void run() {
//				showMessage("Action 1 executed");
//			}
//		};
//		action1.setText("Action 1");
//		action1.setToolTipText("Action 1 tooltip");
//		action1.setImageDescriptor(
//				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
//
//		action2 = new Action() {
//			public void run() {
//				showMessage("Action 2 executed");
//			}
//		};
//		action2.setText("Action 2");
//		action2.setToolTipText("Action 2 tooltip");
//		action2.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				int index = viewer.getTable().getSelectionIndex();
				String viewerID = "stackoverflow.ViewAndDialog.ContentView";

				//Random number to be an ID
				String secondaryId =Double.toString(Math.random());
				try {		
					activeEvent.showView(viewerID, secondaryId, IWorkbenchPage.VIEW_ACTIVATE);
					IViewReference currentView = page.findViewReference(viewerID, secondaryId);
					IViewPart viewPart = currentView.getView(true);
					ContentView myView = (ContentView) viewPart;

					myView.setContent(viewer.getData("questionId" + index).toString());

				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "SearchResult View", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
