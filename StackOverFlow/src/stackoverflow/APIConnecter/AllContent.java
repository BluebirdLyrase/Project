package stackoverflow.APIConnecter;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;
import stackoverflow.DataClass.Comment;

public class AllContent extends StackOverFlowConnecter {

	private Question allConetent;
	private String title;
	private String id;
	private String body;
	private String qOwner;
	private String qOwnerImage;
	private int qScore;
	private boolean haveTags;
	private String[] tags;
	private Comment[] qComment;
	private boolean haveComment;
	private boolean haveAnswer;
	
	
	private Answer[] answer = {};
	private String aBody;
	private int aScore;
	private String aOwner;
	private String aOwnerImage;
	private Comment[] aComment;
	private boolean is_accepted;
	private boolean haveAComment;
	
	private int cScore;
	private String cBody;
	private String cOwner;

	private JSONObject itemObject;

	public JSONObject getJsonObject() {
		return this.json;
	}

	public Question getAllConetent() {
		return allConetent;
	}
	
	public AllContent(String question_id) throws IOException, JSONException {
		 this(question_id,false);
	}

	public AllContent(String question_id,boolean isOffline) throws IOException, JSONException {

		// All Question that can be found by the URL will have an Accepted Answer or

		//Check if AllContent is used for offline content
		if(isOffline) {
			
		}else {
		this.url = "https://api.stackexchange.com/2.2/questions/" + question_id
				+ "?order=asc&sort=activity&site=stackoverflow&filter="
				+ "!6CZol-kjk43Caeu4wbmgfWPFBKTl-6MgX9_mx25H6._QEcG9r2lN3QrdeDe";

		this.json = readJsonFromUrl(this.url);
		}

		this.itemObject = json.getJSONArray("items").getJSONObject(0);

		this.title = itemObject.getString("title");
		LOGGER.info("[" + LOGGER.getName() + "] " + "title : " + title);
		
		int ID =  itemObject.getInt("question_id");
		this.id = Integer.toString(ID);
		LOGGER.info("[" + LOGGER.getName() + "] " + "question_id : " + id);

		this.body = itemObject.getString("body");
		LOGGER.info("[" + LOGGER.getName() + "] " + "body : " + body);
		
		this.qScore = itemObject.getInt("score");
		LOGGER.info("[" + LOGGER.getName() + "] " + "qscore : " + qScore);

		JSONObject qOwnerJson = itemObject.getJSONObject("owner");
		boolean qHaveImage = qOwnerJson.has("profile_image");
		
		haveTags = itemObject.has("tags");
		if(haveTags) {
		JSONArray tagsObjet = itemObject.getJSONArray("tags");
		int tagsLenght = tagsObjet.length();
		this.tags = new String[tagsLenght];
		for(int i = 0;i<tagsLenght;i++) {
			tags[i] = tagsObjet.getString(i);
		} 
		}else {
			this.tags = new String[1];
			tags[0] = "notag";
		}
		
		
		

		this.qOwner = qOwnerJson.getString("display_name");
		LOGGER.info("[" + LOGGER.getName() + "] " + "Q owner : " + qOwner);

		if (qHaveImage) {
			this.qOwnerImage = qOwnerJson.getString("profile_image");
			LOGGER.info("[" + LOGGER.getName() + "] " + "qOwnerImage : " + qOwnerImage);
		} else {
			this.qOwnerImage = "https://www.img.in.th/images/f11865103ab590aff5efd38cbb5f4dbd.png";
			LOGGER.info("[" + LOGGER.getName() + "] " + "qOwnerImage : " + "this man have no Image");
		}

		///////////////////////////// Comment ///////////////////////////////////

		
		int comment_count = itemObject.getInt("comment_count");
		if (comment_count > 0) {
			haveComment = true;
		} else {
			haveComment = false;
		}

		if (haveComment) {
			JSONArray commentObject = itemObject.getJSONArray("comments");

			int commentLenght = commentObject.length();
			LOGGER.info("[" + LOGGER.getName() + "] " + "commentLenght : " + commentLenght);
			qComment = new Comment[commentLenght];
			for (int i = 0; i < commentLenght; i++) {
				cBody = commentObject.getJSONObject(i).getString("body");
				cScore = commentObject.getJSONObject(i).getInt("score");
				cOwner = commentObject.getJSONObject(i).getJSONObject("owner").getString("display_name");
				qComment[i] = new Comment(cBody, cScore, cOwner);
				LOGGER.info("[" + LOGGER.getName() + "] " + "cBody " + i + " : " + qComment[i].getBody());
				LOGGER.info("[" + LOGGER.getName() + "] " + "cScore " + i + " : " + qComment[i].getScore());
				LOGGER.info("[" + LOGGER.getName() + "] " + "cOwner " + i + " : " + qComment[i].getOwner());
			}

		}

		//////////////////////////////////////////////////////////////////////

		int answer_count = itemObject.getInt("answer_count");

		//// determine if this Question have any Answer
		if (answer_count > 0) {
			haveAnswer = true;
		} else {
			haveAnswer = false;
		}

		if (haveAnswer) {

			JSONArray answerObject = itemObject.getJSONArray("answers");

			int answerLenght = answerObject.length();
			LOGGER.info("[" + LOGGER.getName() + "] " + "answerLenght : " + answerLenght);
			answer = new Answer[answerLenght];

			for (int i = 0; i < answerLenght; i++) {

				JSONObject currentAnswerObject = answerObject.getJSONObject(i);

				this.aBody = currentAnswerObject.getString("body");
				LOGGER.info("[" + LOGGER.getName() + "] " + "aBody : " + aBody);

				this.aScore = currentAnswerObject.getInt("score");
				LOGGER.info("[" + LOGGER.getName() + "] " + "aScore : " + aScore);

				this.is_accepted = currentAnswerObject.getBoolean("is_accepted");
				LOGGER.info("[" + LOGGER.getName() + "] " + "is_accepted : " + is_accepted);

				JSONObject aOwnerJson = currentAnswerObject.getJSONObject("owner");
				boolean aHaveImage = aOwnerJson.has("profile_image");

				this.aOwner = aOwnerJson.getString("display_name");
				LOGGER.info("[" + LOGGER.getName() + "] " + "A owner : " + aOwner);

				if (aHaveImage) {
					this.aOwnerImage = aOwnerJson.getString("profile_image");
					LOGGER.info("[" + LOGGER.getName() + "] " + "aOwnerImage : " + aOwnerImage);
				} else {
					this.aOwnerImage = "https://www.img.in.th/images/f11865103ab590aff5efd38cbb5f4dbd.png";
					LOGGER.info("[" + LOGGER.getName() + "] " + "aOwnerImage : " + "this man habe no Image");
				}

				///////////////////////////// Comment///////////////////////////////////

				int AComment_count = currentAnswerObject.getInt("comment_count");

				if (AComment_count > 0) {
					haveAComment = true;
				} else {
					haveAComment = false;
				}
				LOGGER.info("[" + LOGGER.getName() + "] " + "haveAComment : " + haveAComment);

				if (haveAComment) {

					JSONArray currentaCommentObject = currentAnswerObject.getJSONArray("comments");
					int AcommentLenght = currentaCommentObject.length();
					LOGGER.info("[" + LOGGER.getName() + "] " + "AcommentLenght : " + AcommentLenght);
					aComment = new Comment[AcommentLenght];
					for (int j = 0; j < AcommentLenght; j++) {
						cBody = currentaCommentObject.getJSONObject(j).getString("body");
						cScore = currentaCommentObject.getJSONObject(j).getInt("score");
						cOwner = currentaCommentObject.getJSONObject(j).getJSONObject("owner").getString("display_name")
								;
						aComment[j] = new Comment(cBody, cScore, cOwner);
						LOGGER.info("[" + LOGGER.getName() + "] " + "aBody " + i + " : " + aComment[j].getBody());
						LOGGER.info("[" + LOGGER.getName() + "] " + "aScore " + i + " : " + aComment[j].getScore());
						LOGGER.info("[" + LOGGER.getName() + "] " + "aOwner " + i + " : " + aComment[j].getOwner());
					}
				}

				//////////////////////////////////////////////////////////////////////

				answer[i] = new Answer(aBody, aScore, is_accepted, aComment, haveAComment, aOwner, aOwnerImage);
			}

		}

		this.allConetent = new Question(title, body, qComment, answer, haveComment, haveAnswer, qOwner, qOwnerImage,qScore,haveTags,tags,id);

	}

}
