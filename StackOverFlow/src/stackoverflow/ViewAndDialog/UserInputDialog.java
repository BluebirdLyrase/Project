package stackoverflow.ViewAndDialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class UserInputDialog extends TitleAreaDialog {

	private Text txtSearchText;
	private Combo siteCombo;
	private Combo sortCombo;
	private Combo orderCombo;
	private Combo acceptedCombo;
	
	private String SearchText;
	private String order;
	private String sort;
	private String site;
	private boolean acceptedOnly;

	////List of option
	String[] sortList = {"relevance","creation","votes","activity"};
	String[] siteList = {"stackoverflow","devops","gamedev","stackapps","superuser","sqa","softwareengineering","reverseengineering","webapps","webmasters"};
	
	
	
	public UserInputDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Stackoverflow Search");
		setMessage("Search answer from Stackoverflow", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		
		Composite filterContainer = new Composite(area, SWT.NONE);
		filterContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout filterLayout = new GridLayout(4, false);
		filterContainer.setLayout(filterLayout);
		
		
		createSearchText(container);
		createSortFilterText(container);
		createSortCombo(filterContainer);	
		createOrderCombo(filterContainer);
		createSiteCombo(filterContainer);
		createAcceptedCombo(filterContainer);
		

		return area;
	}

	private void createSearchText(Composite container) {
		Label lbtSearchText = new Label(container, SWT.NONE);
		lbtSearchText.setText("Search :");

		GridData dataSearchText = new GridData();
		dataSearchText.grabExcessHorizontalSpace = true;
		dataSearchText.horizontalAlignment = GridData.FILL;

		txtSearchText = new Text(container, SWT.BORDER);
		txtSearchText.setLayoutData(dataSearchText);

	}
	
	
	private void createSortFilterText(Composite container) {

		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		Label filterText = new Label(container, SWT.NONE);
		filterText.setText("Filter: ");
		filterText.setLayoutData(gridData1);
		Label lbtSearchText = new Label(container, SWT.NONE);
		lbtSearchText.setText(" ");
	}
	
	private void createOrderCombo(Composite filterContainer) {
		Label lbtSearchText = new Label(filterContainer, SWT.NONE);
		lbtSearchText.setText("Sort by :");

		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		orderCombo = new Combo(filterContainer , SWT.DROP_DOWN | SWT.READ_ONLY);
		orderCombo.setLayoutData(gridData1);
		orderCombo.add("ASC");
		orderCombo.add("DESC");
		orderCombo.select(0);
	}

	
	private void createSortCombo(Composite filterContainer) {
		Label lbtSearchText = new Label(filterContainer, SWT.NONE);
		lbtSearchText.setText("Oder by :");
		
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		sortCombo = new Combo(filterContainer , SWT.DROP_DOWN | SWT.READ_ONLY);
		sortCombo.setLayoutData(gridData1);
		for(String sortBy : sortList) {
			sortCombo.add(sortBy);
		}
		sortCombo.select(0);
	}
	
	private void createSiteCombo(Composite filterContainer) {
		
		Label lbtSearchText = new Label(filterContainer, SWT.NONE);
		lbtSearchText.setText("Site :");
		
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		siteCombo = new Combo(filterContainer , SWT.DROP_DOWN | SWT.READ_ONLY);
		siteCombo.setLayoutData(gridData1);
		for(String siteName : siteList) {
			siteCombo.add(siteName);
		}
		siteCombo.select(0);
	}
	
	private void createAcceptedCombo(Composite filterContainer) {
		Label lbtSearchText = new Label(filterContainer, SWT.NONE);
		lbtSearchText.setText("Number of Answer :");
		
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = false;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		acceptedCombo = new Combo(filterContainer , SWT.DROP_DOWN | SWT.READ_ONLY);
		acceptedCombo.setLayoutData(gridData1);
		acceptedCombo.add("All Answer");
		acceptedCombo.add("Accepted Answer Only");
		acceptedCombo.select(0);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		SearchText = txtSearchText.getText();
		order = orderCombo.getText();
		sort = sortCombo.getText();
		site = siteCombo.getText();
		setAccepted(acceptedCombo.getText());

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getSearchText() {
		return SearchText;
	}
	public String getOrder() {
		return order;
	}

	public String getSort() {
		return sort;
	}

	public String getSite() {
		return site;
	}
	
	private void setAccepted(String isAccepted) {
		if(isAccepted.equals("Accepted Answer Only")) {
		this.acceptedOnly = true;
		}else {
			this.acceptedOnly = false;
		}
	}

	public boolean isAcceptedOnly() {
		return acceptedOnly;
	}

}
