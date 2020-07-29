package stackoverflow.ViewAndDialog;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SearchHistorySearchDialog extends TitleAreaDialog {

	private Text txtSearchText;
	private Text txtTagsText;
	
	private Combo siteCombo;
	private Combo sortCombo;
	private Combo orderCombo;
	
	private String SearchText;
	private String TagsText;
	private String order;
	private String sort;
	private String site;

	////List of option
	private String[] sortList = {"any","relevance","creation","votes","activity"};
	private String[] siteList = {"any","stackoverflow","devops","gamedev","stackapps","superuser","sqa","softwareengineering","reverseengineering","webapps","webmasters"};
	private String[] orderList = {"any","ASC","DESC"};
	
	public SearchHistorySearchDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Local Table Search");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(1, false);
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
		createTagsText(filterContainer);
		

		return area;
	}

	private void createSearchText(Composite container) {
		Label lbtSearchText = new Label(container, SWT.NONE);
		lbtSearchText.setText("Search Text :");
		lbtSearchText.setFont(new org.eclipse.swt.graphics.Font(null, "", 10, SWT.BOLD));

		GridData dataSearchText = new GridData();
		dataSearchText.grabExcessHorizontalSpace = true;
		dataSearchText.horizontalAlignment = GridData.FILL;

		txtSearchText = new Text(container, SWT.BORDER);
		txtSearchText.setLayoutData(dataSearchText);

	}
	
	private void createTagsText(Composite container) {
		Label lbtSearchText = new Label(container, SWT.NONE);
		lbtSearchText.setText("Tagged :");
		
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;

		txtTagsText = new Text(container, SWT.BORDER);
		txtTagsText.setLayoutData(gridData1);

	}
	
	
	private void createSortFilterText(Composite container) {

		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		
		Label filterText = new Label(container, SWT.NONE | SWT.BOLD);
		
		filterText.setFont(new org.eclipse.swt.graphics.Font(null, "", 10, SWT.BOLD));
		filterText.setText("Filter");
		filterText.setLayoutData(gridData1);
	}
	
	private void createOrderCombo(Composite filterContainer) {
		Label lbtSearchText = new Label(filterContainer, SWT.NONE);
		lbtSearchText.setText("Order :");

		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.horizontalAlignment = GridData.BEGINNING;
		orderCombo = new Combo(filterContainer , SWT.DROP_DOWN | SWT.READ_ONLY);
		orderCombo.setLayoutData(gridData1);
		for(String sortBy : orderList) {
			orderCombo.add(sortBy);
		}
		orderCombo.select(0);
	}

	
	private void createSortCombo(Composite filterContainer) {
		Label lbtSearchText = new Label(filterContainer, SWT.NONE);
		lbtSearchText.setText("Sort by :");
		
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
		TagsText = txtTagsText.getText();

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}
	
	public String getTagsText() {
		return TagsText;
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

}
