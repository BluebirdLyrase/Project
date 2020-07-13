package stackoverflow.ViewAndDialog;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.*;
import org.json.JSONException;
import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Comment;
import stackoverflow.DataClass.Question;
import stackoverflow.LocalJsonConnector.ViewAutoWriter;

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
		String questionOwner = "";
		String answerOwner = "";
		String HTMLbody;
		String HTMLHeader = "    <style>\r\n" + "        code {\r\n" + "            background-color: #eff0f1;\r\n"
				+ "        }\r\n" + "\r\n" + "        .userImg {\r\n" + "            box-shadow: 0px 2px 2px black;\r\n"
				+ "            width: 50px;\r\n" + "            height: 50px;\r\n" + "        }\r\n" + "\r\n"
				+ "        * {\r\n" + "            box-sizing: border-box;\r\n" + "        }\r\n" + "\r\n"
				+ "        .column {\r\n" + "            float: left;\r\n" + "            width: 50%;\r\n"
				+ "        }\r\n" + "\r\n" + "        .row:after {\r\n" + "            content: \"\";\r\n"
				+ "            display: table;\r\n" + "            clear: both;\r\n" + "        }\r\n"
				+ "        button.link { background:none;border:none;color:blue; }\r\n"
				+ ".accepted{border-style:solid;border-color:#00C851;}" + "    </style>";

		String script = "<script>\r\n" + "    function showComment(id) {\r\n"
				+ "        console.log(\"btn clicked\");\r\n" + "        if(id!='comment'){\r\n"
				+ "        var x = document.getElementById(\"aCommentID\"+id);\r\n"
				+ "        console.log(\"id=\"+id);\r\n" + "        }\r\n" + "        else{\r\n"
				+ "        var x = document.getElementById(\"qCommentID\");\r\n"
				+ "        console.log(\"id=\"+id);\r\n" + "        }\r\n" + "            \r\n" + "\r\n"
				+ "        if (x.style.display === \"block\") {\r\n" + "            x.style.display = \"none\";\r\n"
				+ "        } else {\r\n" + "            x.style.display = \"block\";\r\n" + "        }\r\n"
				+ "    }\r\n" + "</script>";
		String tags = "";

		try {
			content = new AllContent(id);
			Question q = content.getAllConetent();

			////// Question
			question = "<h2>Question : " + q.getTitle() + "</h2>" + "<div style=\" font-size: 18px \"> " + q.getBody()
					+ "</div><hr>";

			new ViewAutoWriter().saveContentViewHistory(id, q.getTags(), q.getTitle());

			for (int t = 0; t < q.getTags().length; t++) {
				tags = tags + "<code>" + q.getTags()[t] + "</code> ";
			}

			if (q.isHaveComment()) {

				Comment[] comment = q.getComment();
				questionComment = "<button class=\"link\" onclick=\"showComment('comment')\">show comments</button><div  id=\"qCommentID\" style=\" background-color:#e4f1f7; display:none;\">";
				for (int i = 0; i < comment.length; i++) {
					questionComment = questionComment
							+ "<div style=\" margin-right: 5%; margin-left: 5%;  font-size: 14px; \"><B><a style=\"color:#f2af6f;\" title=\"Comment Score\">"
							+ comment[i].getScore() + " </a>" + q.getOwner() + "</B>" + comment[i].getBody()
							+ "</div><hr style=\"color: #DCDCDC; background-color: #DCDCDC; margin-left:20px;\">";
				}
				questionComment = questionComment + "</div>";
			} else {
				questionComment = "";
			}

			questionOwner = "<div style=\"background-color: #C2E4F2;\">" + "<div class=\"row\">"
					+ "<div class=\"column\">" + "<h4 style=\"color: #04446E; margin-left:1%\" >" + "<img src=\""
					+ q.getOwnerImage() + "\" class=\"userImg\" title=\"User Image\"><br>" + q.getOwner() + "</h4>"
					+ "</div>" + " <div class=\"column\" ><br> \r\n" + " Score: <a style=\"color:#F98B21;\">"
					+ q.getScore() + "</a> <br> Tag : " + tags + "</div>" + "</div>"
					+ "<hr style=\"color: white; background-color: white; box-shadow: 0px 5px 5px black;\"></div>";

			if (q.isHaveAnswer()) {
				Answer[] answers = q.getAnswer();

				for (int i = 0; i < answers.length; i++) {

					answer = answer + ("<div class=\"none" + i + "\"><h2>Answer #" + (i + 1) + "</h2>"
							+ "<div style=\" font-size: 16px \"> " + answers[i].getBody() + "</div><hr>");
					if (answers[i].isAccepted()) {
						System.out.println(i + "isaccepted");
						answer = answer.replaceAll("<div class=\"none" + i + "\"><h2>Answer #", "<div class=\"none"+ i +" \"><h2 tilte=\"Accepted answer\" style=\"color:#00C851\">✅Answer #");
					}

					if (answers[i].isHaveComment()) {
						Comment[] aComment = answers[i].getComment();
						answerComment = "<button class=\"link\" onclick=\"showComment(" + i
								+ ")\">show comments </button><div id=\"aCommentID" + i
								+ "\" style=\"background-color:#e4f1f7; display:none;\">";
						for (int j = 0; j < answers[i].getComment().length; j++) {
							answerComment = answerComment
									+ "<div style=\" margin-right: 5%; margin-left: 5%; font-size: 14px; \"><a style=\"color:#f2af6f;\" title=\"Comment Score\"><B>"
									+ aComment[j].getScore() + " </a>" + aComment[j].getOwner() + "  </B>"
									+ aComment[j].getBody()
									+ "</div><br><hr style=\"color: #DCDCDC; background-color: #DCDCDC;margin-left:20px;\">";

						}
						answerComment = answerComment + "</div>";

					} else {
						answerComment = "";
					}

					answerOwner = "<div style=\"background-color: #C2E4F2;\">" + "<div class=\"row\">"
							+ "<div class=\"column\">" + "<h4 style=\"color: #04446E; margin-left:1%\" >"
							+ "<img src=\"" + answers[i].getOwnerImage()
							+ "\" class=\"userImg\" title=\"User Image\"><br>" + answers[i].getOwner() + "</h4>"
							+ "</div>" + " <div class=\"column\" ><br> \r\n" + " Score: <a style=\"color:#F98B21;\">"
							+ answers[i].getScore() + " </a></div>" + "</div>"
							+ "<hr style=\"color: white; background-color: white; box-shadow: 0px 5px 5px black;\"></div>";

					answer = answer + answerComment + answerOwner + "</div>";

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
		HTMLbody = HTMLHeader + question + questionComment + questionOwner + answer + script;
		String codeBgColor = "background-color: #eff0f1;";
		HTMLbody = HTMLbody.replaceAll("<pre", "<pre style=\"" + codeBgColor + "\"");

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
