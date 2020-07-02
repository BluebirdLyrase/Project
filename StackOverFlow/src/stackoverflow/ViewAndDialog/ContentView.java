package stackoverflow.ViewAndDialog;

import org.eclipse.swt.dnd.*;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.*;
import org.json.JSONException;
import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Comment;
import stackoverflow.DataClass.Question;
import java.io.IOException;
import javax.inject.Inject;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.*;

public class ContentView extends ViewPart {

	@Inject
	IWorkbench workbench;

	Composite parent;
	public static final String ID = "stackoverflow.ViewAndDialog.ContentView";

	private String id = null;
	
	public void setContent(String id) {
		this.id = id;
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;

		Browser browser;

		try {
			browser = new Browser(parent, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}

		browser.setText(createHtml());

	}

	private String createHtml() {
		AllContent content;
		String answer = "";
		String question = "";
		String questionComment = "";
		String answerComment = "";
		String questionOwner="";
		String answerOwner="";
		String HTMLbody;
		String HTMLHeader="<header>\r\n" + 
				"    <style>\r\n" + 
				"        code {\r\n" + 
				"            background-color: #eff0f1;\r\n" + 
				"        }\r\n" + 
				"    </style>\r\n" + 
				"</header>";

		try {
			content = new AllContent(id);
			Question q = content.getAllConetent();

			////// Question
			question = "<h2>Question : " + q.getTitle() + "</h2>" + "<div style=\" font-size: 18px \"> " + q.getBody()
					+ "</div><hr>";
			if (q.isHaveComment()) {

				Comment[] comment = q.getComment();
				System.out.println(comment.length);
				for (int i = 0; i < comment.length; i++) {
					questionComment = questionComment
							+ "<div style=\" margin-right: 5%; margin-left: 5%; font-size: 14px; \"><B>comment #</B>"
							+ (i + 1) + comment[i].getBody()
							+ "<hr style=\"color: #DCDCDC; background-color: #DCDCDC;\"></div>";

				}
				questionOwner=
						 "<div style=\"background-color: #C2E4F2;\">"
						+"<h3 style=\"color: #005C84;\" >"
						+ q.getOwner()
						+"<br><img src=\"https://www.img.in.th/images/f11865103ab590aff5efd38cbb5f4dbd.png\" title=\"User Image\" width=\"100\" height=\"100\" ></h3>"
						+"<hr style=\"color: white; background-color: white; box-shadow: 0px 5px 5px black;\"></div>";
				
			}

			////// Answer
			if (q.isHaveAnswer()) {
				Answer[] answers = q.getAnswer();

				for (int i = 0; i < answers.length; i++) {

					answer = answer + ("<h2>Answer #" + (i + 1) + "</h2>" + "<div style=\" font-size: 16px \"> "
							+ answers[i].getBody() + "</div><hr>");
					answerOwner=
							 "<div style=\"background-color: #C2E4F2;\">"
							+"<h3 style=\"color: #005C84;\" >"
							+ answers[i].getOwner()
							+"<br><img src=\"https://www.img.in.th/images/f11865103ab590aff5efd38cbb5f4dbd.png\" title=\"User Image\" width=\"100\" height=\"100\" ></h3>"
							+"<hr style=\"color: white; background-color: white; box-shadow: 0px 5px 5px black;\"></div>";
					
					if (answers[i].isHaveComment()) {
						Comment[] aComment = answers[i].getComment();
						for (int j = 0; j < answers[i].getComment().length; j++) {
							answerComment = answerComment
									+ "<div style=\" margin-right: 5%; margin-left: 5%; font-size: 14px; \"><B>comment #</B>"
									+ (j + 1) + aComment[j].getBody()
									+ "<br><hr style=\"color: #DCDCDC; background-color: #DCDCDC;\"></div>";
							
							
						}
						
						answer = answer + answerComment+answerOwner;
					}
				}
			} else {
				answer = "<h2>No Answer</h2>";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HTMLbody =HTMLHeader+ question+ questionComment+questionOwner+ answer;
		String codeBgColor = "background-color: #eff0f1;";
		HTMLbody = HTMLbody.replaceAll("<pre", "<pre style=\""+codeBgColor+"\"");

		return HTMLbody;

	}

	@Override
	public void createPartControl(Composite parent) {
		this.parent = parent;
	}

	@Override
	public void setFocus() {
	}

}
