package stackoverflow.ViewAndDialog;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.*;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;

import java.io.IOException;

import javax.inject.Inject;

//import org.eclipse.jface.viewers.*;
//import org.eclipse.swt.graphics.Image;
//import org.eclipse.jface.action.*;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.ui.*;
//import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

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

public class ContentView extends ViewPart {

	@Inject
	IWorkbench workbench;

	Composite parent;
	public static final String ID = "stackoverflow.ViewAndDialog.ContentView";

	@Override
	public void createPartControl(Composite parent) {
		
		
		
		Control[] children = parent.getChildren();
		System.out.println("Children = "+children.length);
		int length = children.length;
		for(int i = 0;i<length;i++) {
			System.out.println(children[i]+" "+i);
			children[i].dispose();
		}
		
		
		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = new Composite(sc, SWT.BORDER | SWT.WRAP  | SWT.MULTI);
		sc.setContent(composite);

		composite.setLayout(new GridLayout(2, false));

		AllContent c;
		try {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;

			composite.setLayout(gridLayout);
			c = new AllContent("62170002");

			Question q = c.getAllConetent();
			System.out.println(q.getBody());
			System.out.println(q.getTitle());

			Label qTitle = new Label(composite, SWT.FILL);

			Label qBody = new Label(composite, SWT.FILL);

			qTitle.setText(q.getTitle());

			qTitle.setFont(new Font(null, "Times New Roman", 15, SWT.BOLD | SWT.ITALIC));
			Label separator = new Label(composite, SWT.SEPARATOR | SWT.SHADOW_OUT | SWT.HORIZONTAL);
			separator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			qBody.setText(q.getBody());
			qBody.setFont(new Font(null, "Times New Roman", 12, SWT.WRAP| SWT.READ_ONLY));
			qBody.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ));
			Label commentHeader = new Label(composite, SWT.BOLD|SWT.READ_ONLY);
			commentHeader.setText("Comment is here");

			Color commentColor = new Color(null, 197, 197, 197);

			if (q.isHaveComment()) {

				String[] comment = q.getComment();
				System.out.println(comment.length);
				Label[] lComment = new Label[comment.length];
				for (int i = 0; i < comment.length; i++) {
					System.out.println(comment[i]);
					lComment[i] = new Label(composite, SWT.NONE);
					lComment[i].setText(comment[i]);
					lComment[i].setBackground(commentColor);
				}

			}

			if (q.isHaveAnswer()) {

				Answer[] answers = q.getAnswer();
				Label[] lAnswers = new Label[answers.length];
				Label[] lAnswersHeader = new Label[answers.length];

				for (int i = 0; i < answers.length; i++) {
					System.out.println("Loop i : " + i);
					System.out.println(answers[i].getBody());
					System.out.println(answers[i].getScore());
					lAnswersHeader[i] = new Label(composite, SWT.NONE);
					lAnswersHeader[i].setText("Answer index " + i);
					lAnswers[i] = new Label(composite, SWT.NONE);
					lAnswers[i].setText(answers[i].getBody());

					if (answers[i].isHaveComment()) {

						String[] aComment = answers[i].getComment();
						Label[] lAComment = new Label[answers[i].getComment().length];

						for (int j = 0; j < answers[i].getComment().length; j++) {
							System.out.println("Loop j : " + j);
							System.out.println(aComment[j]);
							lAComment[j] = new Label(composite, SWT.NONE);
							lAComment[j].setText(aComment[j]);
							lAComment[j].setBackground(commentColor);
						}
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		parent.pack();
		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	@Override
	public void setFocus() {
	}

}
