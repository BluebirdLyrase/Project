package stackoverflow.ViewAndDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TableSearchDialog extends TitleAreaDialog {

	private Text txtSearchText;
	private Text txtTagsText;
	
	private String SearchText;
	private String TagsText;

	private String selectedText = "";
	
	public TableSearchDialog(Shell parentShell) {
		super(parentShell);
	}
	
	public void setText(String selectedText) {
		this.selectedText = selectedText;
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
		createTagsText(filterContainer);
		

		return area;
	}

	private void createSearchText(Composite container) {
		Label lbtSearchText = new Label(container, SWT.NONE);
		lbtSearchText.setText("Search :");
		lbtSearchText.setFont(new org.eclipse.swt.graphics.Font(null, "", 10, SWT.BOLD));

		GridData dataSearchText = new GridData();
		dataSearchText.grabExcessHorizontalSpace = true;
		dataSearchText.horizontalAlignment = GridData.FILL;

		txtSearchText = new Text(container, SWT.BORDER);
		txtSearchText.setLayoutData(dataSearchText);
		txtSearchText.setText(selectedText);

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
	

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		SearchText = txtSearchText.getText();
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

}
