package testinput.handlers;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class UserInputDialog extends TitleAreaDialog {

	private Text txtSearchText;
	private Text txtTagText;

	private String SearchText;
	private String TagText;

	public UserInputDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	public void create() {
		super.create();
		setTitle("This is my first custom dialog");
		setMessage("This is a TitleAreaDialog", IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		createSearchText(container);
		createTagText(container);

		return area;
	}

	private void createSearchText(Composite container) {
		Label lbtSearchText = new Label(container, SWT.NONE);
		lbtSearchText.setText("Search");

		GridData dataSearchText = new GridData();
		dataSearchText.grabExcessHorizontalSpace = true;
		dataSearchText.horizontalAlignment = GridData.FILL;

		txtSearchText = new Text(container, SWT.BORDER);
		txtSearchText.setLayoutData(dataSearchText);
	}

	private void createTagText(Composite container) {
		Label lbtTagText = new Label(container, SWT.NONE);
		lbtTagText.setText("TAG");

		GridData dataTagText = new GridData();
		dataTagText.grabExcessHorizontalSpace = true;
		dataTagText.horizontalAlignment = GridData.FILL;
		txtTagText = new Text(container, SWT.BORDER);
		txtTagText.setLayoutData(dataTagText);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes
	private void saveInput() {
		SearchText = txtSearchText.getText();
		TagText = txtTagText.getText();

	}

	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	public String getSearchText() {
		return SearchText;
	}

	public String getTagText() {
		return TagText;
	}
}
