package stackoverflow.database;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.LocalJsonConnector.Log;

public class PinnedQuestionDelete extends APIConnectorDelete{
	private String userID ;
	private String msg = "";
	
	//databaseID parameter is databaseIDList[i] in viewer
	public String deletePinned(String databaseID) {
		Account account = new Account();
		
		try {
			this.userID = account.getUserID();
			if (account.isLoggedIn()) {
				userID = account.getUserID();
				JSONObject newData = new JSONObject();
				newData.put("_id", databaseID);
				newData.put("UserID", userID);
				msg = databaseDelete(newData.toString(),"deletePinned");
			}else {	
				msg = "This message is impossible to reach";
			}
		} catch (JSONException | IOException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
		
		
		return msg;
	}

}
