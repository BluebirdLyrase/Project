package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class PinnedQuestionList extends APIConnectorGet {

	private String[] titleList;
	private String[] questionIdList;
	private String[] siteList;
	private String[] ownerID;
	private String[] pinText;
	private JSONObject json;

	private int lenght;

	
	public PinnedQuestionList(){
		try {
			this.json = readJsonFromUrl("PinQuestion");
			this.lenght = json.getJSONArray("item").length();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[] getTitleList() throws JSONException {
		titleList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			titleList[i] = json.getJSONArray("item").getJSONObject(i).getString("Title");
		}
		return titleList;
	}


	public String[] getQuestionIdList() throws JSONException {
		questionIdList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			questionIdList[i] = json.getJSONArray("item").getJSONObject(i).get("ID").toString();
		}
		return questionIdList;
	}


	public String[] getSiteList() throws JSONException {
		siteList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			siteList[i] = json.getJSONArray("item").getJSONObject(i).get("Site").toString();
		}
		return siteList;
	}


	public String[] getOwnerID() throws JSONException {
		ownerID = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			ownerID[i] = json.getJSONArray("item").getJSONObject(i).get("UserID").toString();
		}
		return ownerID;
	}


	public String[] getPinText() throws JSONException {
		pinText = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			pinText[i] = json.getJSONArray("item").getJSONObject(i).get("PinText").toString();
		}
		return pinText;
	}

	public int getLenght() {
		return lenght;
	}

	
}
