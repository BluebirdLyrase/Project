package stackoverflow.database;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.LocalJsonConnector.Log;

public class PinnedQuestionWriter extends APIConnectorPost{

	private String userID;
	private String date;

//This Method will return the message to use in Message Dialog
	public String pinnedWriter(String ID, String site,String title,String pinText) {
		String msg = "";
		Account account = new Account();
		try {
			if (account.isLoggedIn()) {
				
				this.userID = account.getUserID();
				this.date = LocalDateTime.now().toString();
				JSONObject newData = new JSONObject();
				newData.put("ID", ID);
				newData.put("UserID", userID);
				newData.put("Title", title);
				newData.put("PinText", pinText);
				newData.put("Date", date);
				newData.put("Site", site);
				msg = databaseWriter(newData.toString(),"addPin");
				
			}else {
				msg = "this feature is only available while logged in serve";
			}
		} catch (JSONException | IOException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}

		return msg;
	}

}
