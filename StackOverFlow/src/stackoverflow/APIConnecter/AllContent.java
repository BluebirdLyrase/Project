package stackoverflow.APIConnecter;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;
import stackoverflow.LocalJsonConnector.DefaultDir;
import stackoverflow.LocalJsonConnector.JSONFile;
import stackoverflow.DataClass.Comment;
/* 
 * use Structure from stackoverflow.Dataclass to create JSONObject 
 * using question_id as a key to receive specific data from  API or local file
 * url https://api.stackexchange.com/2.2/questions/891643?order=asc&sort=activity&site=stackoverflow&filter=!6CZol-kjk43Caeu4wbmgfWPFBKTl-6MgX9_mx25H6._QEcG9r2lN3QrdeDe
 */
public class AllContent extends StackExchangeConnecter {

	private Question allContent;
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


	
	public Question getAllConetent() {
		return allContent;
	}
	
	/*
	 * using question_id as a key to receive specific data from  API or local file
	 * if is Offline is true question_id.json = filename for local file
	 * if is Offline is false question_id.json = an id of quetion in API
	 */
	
	public AllContent(String question_id,boolean isOffline,String site) throws IOException, JSONException {

		// All Question that can be found by the URL will have an Accepted Answer or
		//Check if AllContent is used for offline content
		if(isOffline) {
			JSONFile local = new JSONFile();
			String defaultDir = new DefaultDir().getDefaultDir();
			String fileDirURL = (defaultDir + "\\StackOverFlowHelper\\OfflineContent\\"+question_id);
			this.json = local.parseJSONFile(fileDirURL);
			
		}else {
		this.url = "https://api.stackexchange.com/2.2/questions/" + question_id
				+ "?order=asc&sort=activity&site="+site+"&filter="
				+ "!6CZol-kjk43Caeu4wbmgfWPFBKTl-6MgX9_mx25H6._QEcG9r2lN3QrdeDe";

		this.json = readJsonFromUrl(this.url);
		}

		this.itemObject = json.getJSONArray("items").getJSONObject(0);

		this.title = itemObject.getString("title");
		LOGGER.info("title : " + title);
		
		///catch and fix API bug : ( it's not my fault

		this.id=  question_id;

		LOGGER.info("question_id : " + id);

		this.body = itemObject.getString("body");
		LOGGER.info("body : " + body);
		
		this.qScore = itemObject.getInt("score");
		LOGGER.info("qscore : " + qScore);

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
		
		///catch and fix API bug : ( it's not my fault
		try {
			this.qOwner = qOwnerJson.getString("display_name");
		}catch(JSONException e)
		{
			LOGGER.severe("no display_name bug on q Owner");
			this.qOwner = "Unavailable";
		}

		
		LOGGER.info("Qowner : " + qOwner);

		if (qHaveImage) {
			this.qOwnerImage = qOwnerJson.getString("profile_image");
			LOGGER.info("qOwnerImage : " + qOwnerImage);
		} else {
			this.qOwnerImage = "https://www.img.in.th/images/f11865103ab590aff5efd38cbb5f4dbd.png";
			LOGGER.info("qOwnerImage : " + "this man have no Image");
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
			LOGGER.info("commentLenght : " + commentLenght);
			qComment = new Comment[commentLenght];
			for (int i = 0; i < commentLenght; i++) {
				cBody = commentObject.getJSONObject(i).getString("body");
				cScore = commentObject.getJSONObject(i).getInt("score");
				cOwner = commentObject.getJSONObject(i).getJSONObject("owner").getString("display_name");
				qComment[i] = new Comment(cBody, cScore, cOwner);
				LOGGER.info("cBody " + i + " : " + qComment[i].getBody());
				LOGGER.info("cScore " + i + " : " + qComment[i].getScore());
				LOGGER.info("cOwner " + i + " : " + qComment[i].getOwner());
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
			LOGGER.info("answerLenght : " + answerLenght);
			answer = new Answer[answerLenght];

			for (int i = 0; i < answerLenght; i++) {

				JSONObject currentAnswerObject = answerObject.getJSONObject(i);

				this.aBody = currentAnswerObject.getString("body");
				LOGGER.info("aBody : " + aBody);

				this.aScore = currentAnswerObject.getInt("score");
				LOGGER.info("aScore : " + aScore);

				this.is_accepted = currentAnswerObject.getBoolean("is_accepted");
				LOGGER.info("is_accepted : " + is_accepted);

				JSONObject aOwnerJson = currentAnswerObject.getJSONObject("owner");
				boolean aHaveImage = aOwnerJson.has("profile_image");
				
				///catch and fix API bug : ( it's not my fault
				try {
					this.aOwner = aOwnerJson.getString("display_name");
				}catch(JSONException e)
				{
					LOGGER.severe("no display_name bug on aOwner : "+i);
					this.aOwner = "Unavailable";
				}
				
				
				LOGGER.info("A owner : " + aOwner);

				if (aHaveImage) {
					this.aOwnerImage = aOwnerJson.getString("profile_image");
					LOGGER.info("aOwnerImage : " + aOwnerImage);
				} else {
					this.aOwnerImage = "https://www.img.in.th/images/f11865103ab590aff5efd38cbb5f4dbd.png";
					LOGGER.info("aOwnerImage : " + "this man habe no Image");
				}

				///////////////////////////// Comment///////////////////////////////////

				int AComment_count = currentAnswerObject.getInt("comment_count");

				if (AComment_count > 0) {
					haveAComment = true;
				} else {
					haveAComment = false;
				}
				LOGGER.info("haveAComment : " + haveAComment);

				if (haveAComment) {

					JSONArray currentaCommentObject = currentAnswerObject.getJSONArray("comments");
					int AcommentLenght = currentaCommentObject.length();
					LOGGER.info("AcommentLenght : " + AcommentLenght);
					aComment = new Comment[AcommentLenght];
					for (int j = 0; j < AcommentLenght; j++) {
						cBody = currentaCommentObject.getJSONObject(j).getString("body");
						cScore = currentaCommentObject.getJSONObject(j).getInt("score");
						cOwner = currentaCommentObject.getJSONObject(j).getJSONObject("owner").getString("display_name")
								;
						aComment[j] = new Comment(cBody, cScore, cOwner);
						LOGGER.info("aBody " + i + " : " + aComment[j].getBody());
						LOGGER.info("aScore " + i + " : " + aComment[j].getScore());
						LOGGER.info("aOwner " + i + " : " + aComment[j].getOwner());
					}
				}

				//////////////////////////////////////////////////////////////////////

				answer[i] = new Answer(aBody, aScore, is_accepted, aComment, haveAComment, aOwner, aOwnerImage);
			}

		}

		this.allContent = new Question(title, body, qComment, answer, haveComment, haveAnswer, qOwner, qOwnerImage,qScore,haveTags,tags,id,site);

	}

}
