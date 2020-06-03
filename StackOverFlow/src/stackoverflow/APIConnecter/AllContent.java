package stackoverflow.APIConnecter;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;

public class AllContent extends StackOverFlowConnecter {

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

	public Question getAllConetent() {
		return allConetent;
	}

	public AllContent(String question_id) throws IOException, JSONException {

		this.url = "https://api.stackexchange.com/2.2/questions/" + question_id
				+ "?order=asc&sort=activity&site=stackoverflow&filter=!17vhYVGJF_V1pgWDBcdcFef.pkvM6EAc7IQdcgIj1EtLIL";
		this.json = readJsonFromUrl(this.url);
		
		JSONObject itemObject = json.getJSONArray("items").getJSONObject(0);

		this.title = itemObject.get("title").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "title : " + title);

		this.body = itemObject.get("body").toString();
		LOGGER.info("[" + LOGGER.getName() + "] " + "body : " + body);

		String strComment_count = itemObject.get("comment_count").toString();
		int comment_count = Integer.parseInt(strComment_count);
		System.out.println(comment_count);
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
			
			System.out.println(AComment_count);
			
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

			answer[i] = new Answer(aBody, score, is_accepted, Acomment, haveAComment);
		}
		
		}

		this.allConetent = new Question(title, body, comment, answer,haveComment,haveAnswer);

	}

}
