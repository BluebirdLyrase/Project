package stackoverflow.ViewAndDialog;

import java.io.IOException;
import java.text.DecimalFormat;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.window.Window;
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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.json.JSONException;
import stackoverflow.LocalJsonConnector.ContentTitleList;
import stackoverflow.LocalJsonConnector.FavoriteList;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.Log;
import stackoverflow.LocalJsonConnector.SearchingHistoryList;
import stackoverflow.LocalJsonConnector.SearchingWriter;
import stackoverflow.LocalJsonConnector.ViewHistoryList;
import stackoverflow.LocalJsonConnector.ViewWriter;
import stackoverflow.database.Account;
import stackoverflow.LocalJsonConnector.Content;

public class SettingDialog extends Dialog {
	private Text txtDatabaseStatus;
	private Shell parentShell;
	private IWorkbench wb = PlatformUI.getWorkbench();
	private IWorkbenchWindow win = wb.getActiveWorkbenchWindow();

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public SettingDialog(Shell parentShell) {
		super(parentShell);
		setShellStyle(SWT.CLOSE | SWT.MIN | SWT.RESIZE);
		setBlockOnOpen(false);
		this.parentShell = parentShell;
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);

		Composite login_Bar = new Composite(area, SWT.BORDER);

		login_Bar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		txtDatabaseStatus = new Text(login_Bar, SWT.READ_ONLY);
		GridData gd_txtDatabaseStatus = new GridData(SWT.LEFT, SWT.LEFT, false, false, 1, 1);
		gd_txtDatabaseStatus.widthHint = 300;
		gd_txtDatabaseStatus.heightHint = 90;
		txtDatabaseStatus.setLayoutData(gd_txtDatabaseStatus);
		setDatabaseStatusText();
		Button loginBtn = new Button(login_Bar, SWT.NONE);
		loginBtn.setText("Login");
		loginBtn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				LoginDialog dialog = new LoginDialog(parentShell);
				dialog.create();
				if (dialog.open() == Window.OK) {
					try {
						String userID = dialog.getUserID();
						String password = dialog.getPassword();
						String database = dialog.getDatabaseUrl();
						String msg = new Account().Loggin(userID,password,database);
						MessageDialog.openInformation(win.getShell(), "Atention", msg);
					} catch (IOException | JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					setDatabaseStatusText();
				}

			}
		});

		Composite buttonContainer = new Composite(area, SWT.NONE | SWT.BORDER);
		buttonContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout filterLayout = new GridLayout(3, false);
		GridLayout filterLayout2 = new GridLayout(4, false);
		buttonContainer.setLayout(filterLayout);
		login_Bar.setLayout(filterLayout2);

		Button btnLogout = new Button(login_Bar, SWT.NONE);
		btnLogout.setText("Logout");
		btnLogout.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
//	TODO implement logout page here	  	
//		    	  LoginDialog dialog = new LoginDialog(parentShell);  
//				dialog.create();
//				if (dialog.open() == Window.OK);

			}
		});

		Button checkDatabaseStatus = new Button(login_Bar, SWT.NONE);
		checkDatabaseStatus.setText("Refresh");
		checkDatabaseStatus.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				setDatabaseStatusText();
			}
		});

		createButtonForButtonContainer(buttonContainer);
		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	protected void createButtonForButtonContainer(Composite buttonContainer) {
		String pattern = "#.###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		try {

			double OfflineSize = new Content().getSize();
			double favSize = new FavoriteWriter().getSize();
			double searchSize = new SearchingWriter().getSize();
			double viewWriterSize = new ViewWriter().getSize();
			double dAllSize = OfflineSize + favSize + searchSize + viewWriterSize;

			final Text offlineSize = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			offlineSize.setText("Offline contents size: " + decimalFormat.format(OfflineSize) + " KB");
			final Text strOfflineLenght = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			int intOffLenght = new ContentTitleList().getLenght();
			strOfflineLenght.setText(Integer.toString(intOffLenght));
			final Button clearOfflineButton = new Button(buttonContainer, SWT.PUSH);
			clearOfflineButton.setText("Clear Offline");
			clearOfflineButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {
					try {
						if (confirmPopUp("do you want to remove all Offline Content data?")) {
							new Content().clear();
							showMsg("Succesfully remove all OfflineContent data");
							offlineSize.setText("Favorite contents size: 0 KB");
							strOfflineLenght.setText("0");
						}
					} catch (IOException | JSONException e1) {
						new Log().saveLog(e1);
						e1.printStackTrace();
					}
				}
			});

			final Text favoriteSize = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			favoriteSize.setText("Favorite contents size: " + decimalFormat.format(favSize) + " KB");
			final Text strfavLenght = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			int intFavLenght = new FavoriteList().getLenght();
			strfavLenght.setText(Integer.toString(intFavLenght));
			final Button clearFavButton = new Button(buttonContainer, SWT.PUSH);
			clearFavButton.setText("Clear Favorite");
			clearFavButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {

					try {
						if (confirmPopUp("Are you sure, You want to remove all Favorite Data?")) {
							new FavoriteWriter().clear();
							showMsg("Succesfully remove all Favorite data");
							favoriteSize.setText("Favorite contents size: 0 KB");
							strfavLenght.setText("0");
						}
					} catch (IOException | JSONException e1) {
						new Log().saveLog(e1);
						e1.printStackTrace();
					}
				}
			});

			final Text viewHistorySize = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			viewHistorySize.setText("View history contents size: " + decimalFormat.format(viewWriterSize) + " KB");
			final Text StrVHistoryLenght = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			int intVHistoryLenght = new ViewHistoryList().getLenght();
			StrVHistoryLenght.setText(Integer.toString(intVHistoryLenght));
			final Button clearVHisButton = new Button(buttonContainer, SWT.PUSH);
			clearVHisButton.setText("Clear View History");
			clearVHisButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {

					try {
						if (confirmPopUp("Are you sure, You want to remove all View History Data?")) {
							new ViewWriter().clear();
							showMsg("Succesfully remove all OfflineContent data");
							viewHistorySize.setText("View history contents size: 0 KB");
							StrVHistoryLenght.setText("0");
						}
					} catch (IOException | JSONException e1) {
						new Log().saveLog(e1);
						e1.printStackTrace();
					}
				}
			});

			final Text seacrhHistorySize = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			seacrhHistorySize.setText("Search history contents size: " + decimalFormat.format(searchSize) + " KB");
			final Text strSHistoryLenght = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			int intSHistoryLenght = new SearchingHistoryList().getLenght();
			strSHistoryLenght.setText(Integer.toString(intSHistoryLenght));
			final Button clearSearchButton = new Button(buttonContainer, SWT.PUSH);
			clearSearchButton.setText("Clear Seacrh history");
			clearSearchButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {

					try {
						if (confirmPopUp("Are you sure, You want to remove all Searching Histpry Data?")) {
							new SearchingWriter().clear();
							showMsg("Succesfully remove all Searching History data");
							seacrhHistorySize.setText("View history contents size: 0 KB");
							strSHistoryLenght.setText("0");
						}
					} catch (IOException | JSONException e1) {
						new Log().saveLog(e1);
						e1.printStackTrace();
					}
				}
			});

			final Text allSize = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			allSize.setText("All saved contents size: " + decimalFormat.format(dAllSize) + " KB");
			final Text strAllLenght = new Text(buttonContainer, SWT.None | SWT.READ_ONLY);
			strAllLenght.setText("");
			final Button clearAllButton = new Button(buttonContainer, SWT.PUSH);
			clearAllButton.setText("Clear All");
			clearAllButton.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event e) {

					try {
						if (confirmPopUp("Are you sure, You want to Remove All data?")) {
							new Content().clearAll();
							allSize.setText("All saved contents size: 0 KB");
							showMsg("Succesfully remove All data");
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
	
	private void setDatabaseStatusText() {
		String status = "not Logged in" ;
		String userID = "n\\a";
		String url = "n\\a";
		try {
			Account account = new Account();
			if(account.isLoggedIn()) {
			status = account.getConnectionStatus() ;
			userID = account.getUserID();
			url = account.getDatabaseURL();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		txtDatabaseStatus.setText("Database Status: " + status + "\n" +		
				"User ID: " + userID + "\n"+
				"URL: "+url);
	}
	
	private boolean confirmPopUp(String msg) {
		boolean result = MessageDialog.openConfirm(win.getShell(), "Atention", msg);
		return result;
	}
	
	private void showMsg(String msg) {
		MessageDialog.openInformation(win.getShell(), "Atention", msg);
	}

}
