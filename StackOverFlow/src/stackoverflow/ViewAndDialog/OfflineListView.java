package stackoverflow.ViewAndDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Content;
import stackoverflow.LocalJsonConnector.ContentTitleList;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import java.io.IOException;

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

public class OfflineListView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "stackoverflow.ViewAndDialog.OfflineListView";

	@Inject
	IWorkbench workbench;

	private TableViewer viewer;
	private Action open;
	private Action delete;
	private Action doubleClickAction;
	private ContentTitleList contentTitle;
	private String[] filename;

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
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(ArrayContentProvider.getInstance());
		createTable();
		viewer.setLabelProvider(new ViewLabelProvider());

		// Create the help context id for the viewer's control
		workbench.getHelpSystem().setHelp(viewer.getControl(), "StackOverFlow.viewer");
		getSite().setSelectionProvider(viewer);
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	public void createTable() {
		try {
			contentTitle = new ContentTitleList();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		viewer.setInput(null);
		filename = contentTitle.getFilename();
		String[] title = contentTitle.getTitle();
		viewer.setInput(title);
	}

	public void open() {
		int index = viewer.getTable().getSelectionIndex();
		System.out.println(index + "::" + filename[index]);
	}

	public void delete() {
		int index = viewer.getTable().getSelectionIndex();
		try {
			boolean result = new Content().delete(filename[index], index);
			if(result) {createTable();}
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				OfflineListView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(open);
		manager.add(new Separator());
		manager.add(delete);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(open);
		manager.add(delete);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(open);
		manager.add(delete);
	}

	private void makeActions() {
		open = new Action() {
			public void run() {
				open();
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this Question");
		open.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		delete = new Action() {
			public void run() {
				delete();
			}
		};
		delete.setText("Delete");
		delete.setToolTipText("Delete this question");
		delete.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
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

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "OfflineListView", message);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
