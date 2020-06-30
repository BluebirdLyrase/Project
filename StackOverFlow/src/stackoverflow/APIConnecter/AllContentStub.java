package stackoverflow.APIConnecter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Bundle;

import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;

public class AllContentStub {
	
	private Question allConetent;
	private String title;
	private String body;
	private String[] comment = {};
	private Answer[] answer = {};
	private String aBody;
	private String score;
	private boolean is_accepted;
	private String[] Acomment;
	private boolean haveAComment;
	private boolean haveComment;
	private boolean haveAnswer;
	
	protected static final Logger LOGGER = Logger.getLogger(StackOverFlowConnecter.class.getName());
	String url;
	JSONObject json;
	
	protected String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	protected JSONObject readJsonFromFile(File file) throws IOException, JSONException {
		LOGGER.setLevel(Level.WARNING);
		LOGGER.info("["+LOGGER.getName()+"] "+"url : "+url);
		InputStream is = new FileInputStream(file);
		
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
}

	public Question getAllConetent() {
		return allConetent;
	}

	public AllContentStub(String question_id) throws IOException, JSONException {

		this.url = "libs/content.json";
		Bundle bundle = Platform.getBundle("StackOverFlow");
		URL fileURL = bundle.getEntry(url);
		File file = null;
		try {
		    file = new File(FileLocator.resolve(fileURL).toURI());
			this.json = readJsonFromFile(file);
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		
		JSONObject itemObject = json.getJSONArray("items").getJSONObject(0);

		this.title = itemObject.get("title").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "title : " + title);

		this.body = itemObject.get("body").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "body : " + body);

		String strComment_count = itemObject.get("comment_count").toString();
		int comment_count = Integer.parseInt(strComment_count);
//		System.out.println(comment_count);
		if (comment_count > 0) {
			haveComment = true;
		} else {
			haveComment = false;
		}

		if (haveComment) {
			JSONArray commentObject = itemObject.getJSONArray("comments");

			int commentLenght = commentObject.length();
			LOGGER.info("[" + LOGGER.getName() + "] " + "commentLenght : " + commentLenght);
			comment = new String[commentLenght];
			for (int i = 0; i < commentLenght; i++) {
				comment[i] = commentObject.getJSONObject(i).get("body").toString();
				LOGGER.info("[" + LOGGER.getName() + "] " + "comment " + i + " : " + comment[i]);
			}

		}
		
		String strAnswer_count = itemObject.get("answer_count").toString();
		int answer_count = Integer.parseInt(strAnswer_count);
		if(answer_count>0) {
			haveAnswer = true;
		}else {
			haveAnswer = false;
		}
		
		if(haveAnswer) {

		JSONArray answerObject = itemObject.getJSONArray("answers");

		int answerLenght = answerObject.length();
		LOGGER.info("[" + LOGGER.getName() + "] " + "answerLenght : " + answerLenght);
		answer = new Answer[answerLenght];

		for (int i = 0; i < answerLenght; i++) {

			JSONObject currentAnswerObject = answerObject.getJSONObject(i);

			this.aBody = currentAnswerObject.get("body").toString();
			LOGGER.info("[" + LOGGER.getName() + "] " + "aBody : " + aBody);

			this.score = currentAnswerObject.get("score").toString();
			LOGGER.info("[" + LOGGER.getName() + "] " + "score : " + score);

			String strIs_accepted = currentAnswerObject.get("is_accepted").toString();
			this.is_accepted = Boolean.parseBoolean(strIs_accepted);
			LOGGER.info("[" + LOGGER.getName() + "] " + "is_accepted : " + is_accepted);
			
			String strAComment_count = currentAnswerObject.get("comment_count").toString();
			int AComment_count = Integer.parseInt(strAComment_count);
			
//			System.out.println(AComment_count);
			
			if (AComment_count > 0) {
				haveAComment = true;
			} else {
				haveAComment = false;
			}
			LOGGER.info("[" + LOGGER.getName() + "] " + "haveAComment : " + haveAComment);

			if (haveAComment) {
				int AcommentLenght = currentAnswerObject.getJSONArray("comments").length();
				LOGGER.info("[" + LOGGER.getName() + "] " + "AcommentLenght : " + AcommentLenght);
				Acomment = new String[AcommentLenght];
				for (int j = 0; j < AcommentLenght; j++) {
					Acomment[j] = currentAnswerObject.getJSONArray("comments").getJSONObject(j).get("body").toString();
					LOGGER.info("[" + LOGGER.getName() + "] " + "Acomment[" + j + "] : " + Acomment[j]);
				}
			}

			answer[i] = new Answer(aBody, score, is_accepted, Acomment, haveAComment,"inwza");
		}
		
		}

		this.allConetent = new Question(title, body, comment, answer,haveComment,haveAnswer,"inwza");

	}
	

}
