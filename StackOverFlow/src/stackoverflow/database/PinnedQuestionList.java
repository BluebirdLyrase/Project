package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class PinnedQuestionList extends APIConnectorGet {

	private String[] titleList;
	private String[] questionIdList;
	private String[] databaseIdList;
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
	
	public String[] getTitleList() {
		titleList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			try {
				titleList[i] = json.getJSONArray("item").getJSONObject(i).getString("Title");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				titleList[i] = "";
				e.printStackTrace();
			}
		}
		return titleList;
	}


	public String[] getDatabaseIdList() {
		databaseIdList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			try {
				databaseIdList[i] = json.getJSONArray("item").getJSONObject(i).get("_id").toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				databaseIdList[i] = "";
				e.printStackTrace();
			}
		}
		return databaseIdList;
	}

	public String[] getQuestionIdList() {
		questionIdList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			try {
				questionIdList[i] = json.getJSONArray("item").getJSONObject(i).get("ID").toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				questionIdList[i] = "";
				e.printStackTrace();
			}
		}
		return questionIdList;
	}

	public String[] getSiteList() {
		siteList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			try {
				siteList[i] = json.getJSONArray("item").getJSONObject(i).get("Site").toString();
			} catch (JSONException e) {
				siteList[i] = "";
				e.printStackTrace();
			}
		}
		return siteList;
	}


	public String[] getOwnerID() {
		ownerID = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			try {
				ownerID[i] = json.getJSONArray("item").getJSONObject(i).get("UserID").toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				ownerID[i] = "";
				e.printStackTrace();
			}
		}
		return ownerID;
	}


	public String[] getPinText() {
		pinText = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			try {
				pinText[i] = json.getJSONArray("item").getJSONObject(i).get("PinText").toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				pinText[i] = "";
				e.printStackTrace();
			}
		}
		return pinText;
	}

	public int getLenght() {
		return lenght;
	}

	
}
