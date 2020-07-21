package stackoverflow.ViewAndDialog;

import java.io.IOException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.ContentTitleList;
import stackoverflow.LocalJsonConnector.FavoriteList;

public class SettingDialog extends TitleAreaDialog {

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SettingDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		
		Composite buttonContainer = new Composite(area, SWT.NONE);
		buttonContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout filterLayout = new GridLayout(3, false);
		buttonContainer.setLayout(filterLayout);
		
		createButtonForButtonContainer(buttonContainer);
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	
	
	protected void createButtonForButtonContainer(Composite buttonContainer ) {
		try {
		final Text offlineSize = new Text(buttonContainer,SWT.None) ;
		offlineSize.setText("Offline size");
		final Text	strOfflineLenght = new Text(buttonContainer,SWT.None);
		int intOffLenght =	new ContentTitleList().getLenght();
		strOfflineLenght.setText(Integer.toString(intOffLenght));
		final Button clearOfflineButton = new Button(buttonContainer,SWT.PUSH);
		clearOfflineButton.setText("Clear Offline");

		
		
		final Text favoriteSize = new Text(buttonContainer,SWT.None) ;
		favoriteSize.setText("Favorite size");
		final Text	strfavLenght = new Text(buttonContainer,SWT.None);
		int intFavLenght =	new FavoriteList().getLenght();
		strfavLenght.setText(Integer.toString(intFavLenght));
		final Button clearFavButton = new Button(buttonContainer,SWT.PUSH);
		clearFavButton.setText("Clear Favorite");
		
		final Text viewHistorySize = new Text(buttonContainer,SWT.None) ;
		viewHistorySize.setText("View History size");
		final Text	StrVHistoryLenght = new Text(buttonContainer,SWT.None);
		int intVHistoryLenght =	new ContentTitleList().getLenght();
		StrVHistoryLenght.setText(Integer.toString(intVHistoryLenght));
		final Button clearVHisButton = new Button(buttonContainer,SWT.PUSH);
		clearVHisButton.setText("Clear View History");
		
		final Text seacrhHistorySize = new Text(buttonContainer,SWT.None) ;
		seacrhHistorySize.setText("Seacrh history size");
		final Text	strSHistoryLenght = new Text(buttonContainer,SWT.None);
		int intSHistoryLenght =	new ContentTitleList().getLenght();
		strSHistoryLenght.setText(Integer.toString(intSHistoryLenght));
		final Button clearSearchButton = new Button(buttonContainer,SWT.PUSH);
		clearSearchButton.setText("Clear Seacrh history");
		
		final Text allSize = new Text(buttonContainer,SWT.None) ;
		allSize.setText("All size");
		final Text	strAllLenght = new Text(buttonContainer,SWT.None);
		int intAllLenght =	new ContentTitleList().getLenght();
		strAllLenght.setText(Integer.toString(intAllLenght));
		final Button clearAllButton = new Button(buttonContainer,SWT.PUSH);
		clearAllButton.setText("Clear All");
		
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 320);
	}

}
