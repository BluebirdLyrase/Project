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

	public void setContent(String id){
//		composite.setLayout(new GridLayout(2, false));
//		AllContentStub content;

			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 5;
			gridLayout.marginHeight = 5;
			gridLayout.verticalSpacing = 0;
			gridLayout.horizontalSpacing = 0;

//			content = new AllContent(id, true);
			Browser browser;
			
			try {
				browser = new Browser(parent, SWT.NONE);
			} catch (SWTError e) {
				System.out.println("Could not instantiate Browser: " + e.getMessage());
				return;
			}

			
			browser.setText(getHtml(id));


	}
	
	private String getHtml(String id) {
		AllContent content;
		String answer = "";
		String question = "" ;
		String questionComment = "";
		String answerComment = "";
		String HTMLbody;
		
		///HTML related
		String codeBgColor = "\"background-color:powderblue;\"";
		
		try {
		content = new AllContent(id, false);
		Question q = content.getAllConetent();
		question = "<h1>Question</h1>" + "<B>" + q.getTitle() + "</B>" + q.getBody()+"<hr>";
		if (q.isHaveComment()) {

			String[] comment = q.getComment();
			System.out.println(comment.length);
			for (int i = 0; i < comment.length; i++) {
//				System.out.println(comment[i]);
				questionComment = questionComment + "<B>comment #</B>"+ i + comment[i] + "<br>";
				
			}
		}

		if (q.isHaveAnswer()) {
			Answer[] answers = q.getAnswer();

			for (int i = 0; i < answers.length; i++) {

				answer = answer + ("<h1>Answer #"+i+"</h1>"+answers[i].getBody()+"<hr>");

				if (answers[i].isHaveComment()) {
					String[] aComment = answers[i].getComment();
					for (int j = 0; j < answers[i].getComment().length; j++) {
						answerComment = answerComment +"<B>comment #</B>"+ j + aComment[j] + "<br>";
					}
					answer = answer + "<h4>Comment Section</h4>"+answerComment;
				}
			}		
		}else {
			answer = "<h2>No Answer</h2>";
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HTMLbody = question + questionComment + answer;
		
//		HTMLbody = HTMLbody.replaceAll("<code>", "<code style="+codeBgColor+">");
		
		return HTMLbody ;
		
	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
	}

	@Override
	public void setFocus() {
	}
	
	/////highlight <code>
//	this.body = this.body.replaceAll("<code>", "<div style="+codeBgColor+">><code>");
//	this.body = this.body.replaceAll("</code>","</code></div>");
	
//	comment[i] = comment[i].replaceAll("<code>", "<code style="+codeBgColor+">");

}
