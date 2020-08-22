package stackoverflow.APIConnecter;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionID extends StackOverFlowConnecter {
	
	public String getQuestionID(String title) {
		this.url = "https://api.stackexchange.com/2.2/search?page=1&pagesize=1&order=desc&sort=activity&intitle="+title+"&site=stackoverflow&filter=!)8aF*u2F.Dui-Vt";
		String questionID = null;
		try {
			this.json = readJsonFromUrl(this.url);
			JSONObject itemObject = json.getJSONArray("items").getJSONObject(0);
			questionID = itemObject.get("question_id").toString();	
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return questionID;
		
	}

}
