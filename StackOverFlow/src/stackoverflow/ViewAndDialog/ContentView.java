package stackoverflow.ViewAndDialog;

import org.eclipse.swt.dnd.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.*;
import org.json.JSONException;
import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;
import java.io.IOException;
import javax.inject.Inject;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.Color;

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

	public void setContent(String id) {
//		composite.setLayout(new GridLayout(2, false));
//		AllContentStub content;
		AllContent content;
		try {
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;

//			content = new AllContent(id, true);
			content = new AllContent(id, false);

			Question q = content.getAllConetent();
			
			Browser browser;
			try {
				browser = new Browser(parent, SWT.NONE);
			} catch (SWTError e) {
				System.out.println("Could not instantiate Browser: " + e.getMessage());

				return;
			}

			Color commentColor = new Color(null, 197, 197, 197);

			String qText = "<B>" + q.getTitle() + "</B>" + q.getBody()+"<hr>";

			if (q.isHaveComment()) {

				String[] comment = q.getComment();
				System.out.println(comment.length);
				for (int i = 0; i < comment.length; i++) {
					System.out.println(comment[i]);
					
					////new Code to create HTML TEXT HERE
					
				}

			}

			if (q.isHaveAnswer()) {
				String answerBody = "";

				Answer[] answers = q.getAnswer();
				Text[] lAnswers = new Text[answers.length];
				Text[] lAnswersHeader = new Text[answers.length];

				for (int i = 0; i < answers.length; i++) {
					System.out.println("Loop i : " + i);
					System.out.println(answers[i].getBody());
					System.out.println(answers[i].getScore());

					answerBody = answerBody + ("<h1>Answer #"+i+"</h1>"+answers[i].getBody()+"<hr>");

					if (answers[i].isHaveComment()) {

						String[] aComment = answers[i].getComment();
						for (int j = 0; j < answers[i].getComment().length; j++) {
							System.out.println("Loop j : " + j);
							System.out.println(aComment[j]);
							
						////new Code to create HTML TEXT HERE
							
						}
					}
				}

				browser.setText("<h1>Question</h1>" + qText + answerBody);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
	}

	@Override
	public void setFocus() {
	}

}
