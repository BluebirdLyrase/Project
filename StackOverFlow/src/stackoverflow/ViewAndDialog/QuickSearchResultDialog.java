package stackoverflow.ViewAndDialog;

import java.io.IOException;

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
import org.json.JSONException;

import stackoverflow.APIConnecter.QuickSearchResult;

public class QuickSearchResultDialog extends TitleAreaDialog {

	private String resultText;
	private String title;
	private String body;

	public QuickSearchResultDialog(Shell parentShell, String title, String body) {
		super(parentShell);
		this.title = title;
		this.body = body;
	}

	@Override
	public void create() {
		super.create();
		setTitle("This is your search result");
		setMessage(title, IMessageProvider.INFORMATION);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

			stackOverFlowResult(container);


		return area;
	}

	private void stackOverFlowResult(Composite container) {
		Label lbResult = new Label(container, SWT.NONE);

		lbResult.setText(body);

	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	// save content of the Text fields because they get disposed
	// as soon as the Dialog closes

	@Override
	protected void okPressed() {
		super.okPressed();
	}

//	    public String getFirstName() {
//	        return firstName;
//	    }
//
//	    public String getLastName() {
//	        return lastName;
//	    }
}
