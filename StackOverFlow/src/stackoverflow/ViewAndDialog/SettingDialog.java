package stackoverflow.ViewAndDialog;

import java.io.IOException;
import java.text.DecimalFormat;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.json.JSONException;

import stackoverflow.LocalJsonConnector.ContentTitleList;
import stackoverflow.LocalJsonConnector.FavoriteList;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.SearchingHistoryList;
import stackoverflow.LocalJsonConnector.SearchingWriter;
import stackoverflow.LocalJsonConnector.ViewHistoryList;
import stackoverflow.LocalJsonConnector.ViewWriter;
import stackoverflow.LocalJsonConnector.Content;
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
		
		Composite buttonContainer = new Composite(area, SWT.NONE|SWT.BORDER);
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
		String pattern = "#.###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		try {
			
			double OfflineSize = new Content().getSize();
			double favSize= new FavoriteWriter().getSize();
			double searchSize= new SearchingWriter().getSize();
			double viewWriterSize = new ViewWriter().getSize();
			double dAllSize = OfflineSize+favSize+searchSize+viewWriterSize;
			
		final Text offlineSize = new Text(buttonContainer,SWT.None|SWT.READ_ONLY) ;
		offlineSize.setText("Offline contents size: "+decimalFormat.format(OfflineSize)+" KB");
		final Text	strOfflineLenght = new Text(buttonContainer,SWT.None|SWT.READ_ONLY);
		int intOffLenght =	new ContentTitleList().getLenght();
		strOfflineLenght.setText(Integer.toString(intOffLenght));
		final Button clearOfflineButton = new Button(buttonContainer,SWT.PUSH);
		clearOfflineButton.setText("Clear Offline");
		clearOfflineButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {

		          try {
					if(new Content().clear()) {
					offlineSize.setText("Favorite contents size: 0 KB");
					strOfflineLenght.setText("0");
					}
				} catch (IOException | JSONException e1) {
					new Log().saveLog(e1);
					e1.printStackTrace();
				}
		        }
		      });
		
		
		final Text favoriteSize = new Text(buttonContainer,SWT.None|SWT.READ_ONLY) ;
		favoriteSize.setText("Favorite contents size: "+decimalFormat.format(favSize)+" KB");
		final Text	strfavLenght = new Text(buttonContainer,SWT.None|SWT.READ_ONLY);
		int intFavLenght =	new FavoriteList().getLenght();
		strfavLenght.setText(Integer.toString(intFavLenght));
		final Button clearFavButton = new Button(buttonContainer,SWT.PUSH);
		clearFavButton.setText("Clear Favorite");
		clearFavButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {

		          try {
					if(new FavoriteWriter().clear()){
					favoriteSize.setText("Favorite contents size: 0 KB");
					strfavLenght.setText("0");
					}
				} catch (IOException | JSONException e1) {
					new Log().saveLog(e1);
					e1.printStackTrace();
				}
		        }
		      });
		
		
		
		final Text viewHistorySize = new Text(buttonContainer,SWT.None|SWT.READ_ONLY) ;
		viewHistorySize.setText("View history contents size: "+decimalFormat.format(viewWriterSize)+" KB");
		final Text	StrVHistoryLenght = new Text(buttonContainer,SWT.None|SWT.READ_ONLY);
		int intVHistoryLenght =	new ViewHistoryList().getLenght();
		StrVHistoryLenght.setText(Integer.toString(intVHistoryLenght));
		final Button clearVHisButton = new Button(buttonContainer,SWT.PUSH);
		clearVHisButton.setText("Clear View History");
		clearVHisButton.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {

		          try {
					if(new ViewWriter().clear()) {
					viewHistorySize.setText("View history contents size: 0 KB");
					StrVHistoryLenght.setText("0");
					}
				} catch (IOException | JSONException e1) {
					new Log().saveLog(e1);
					e1.printStackTrace();
				}
		        }
		      });
		
		
		
		final Text seacrhHistorySize = new Text(buttonContainer,SWT.None|SWT.READ_ONLY) ;
		seacrhHistorySize.setText("Search history contents size: "+decimalFormat.format(searchSize)+" KB");
		final Text	strSHistoryLenght = new Text(buttonContainer,SWT.None|SWT.READ_ONLY);
		int intSHistoryLenght =	new SearchingHistoryList().getLenght();
		strSHistoryLenght.setText(Integer.toString(intSHistoryLenght));
		final Button clearSearchButton = new Button(buttonContainer,SWT.PUSH);
		clearSearchButton.setText("Clear Seacrh history");
		clearSearchButton.addListener(SWT.Selection,new Listener() {
		      public void handleEvent(Event e) {

		          try { 
					if(new SearchingWriter().clear()) {
					seacrhHistorySize.setText("View history contents size: 0 KB");
					strSHistoryLenght.setText("0");
					}
				} catch (IOException | JSONException e1) {
					new Log().saveLog(e1);
					e1.printStackTrace();
				}
		        }
		      });

		
		final Text allSize = new Text(buttonContainer,SWT.None|SWT.READ_ONLY) ;
		allSize.setText("All saved contents size: "+decimalFormat.format(dAllSize)+" KB");
		final Text	strAllLenght = new Text(buttonContainer,SWT.None|SWT.READ_ONLY);
		strAllLenght.setText("");
		final Button clearAllButton = new Button(buttonContainer,SWT.PUSH);
		clearAllButton.setText("Clear All");
		clearAllButton.addListener(SWT.Selection,new Listener() {
		      public void handleEvent(Event e) {

		          try {
		        	if(new Content().clearAll()) {
		        	allSize.setText("All saved contents size: 0 KB");
		        	}
				
				} catch (IOException | JSONException e1) {
					new Log().saveLog(e1);
					e1.printStackTrace();
				}
		        }
		      });

		} catch (IOException | JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
	}
	

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

 
}
