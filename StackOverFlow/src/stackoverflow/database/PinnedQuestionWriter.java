package stackoverflow.database;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

public class PinnedQuestionWriter extends DatabaseConnectorPost{

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
				databaseWriter(newData.toString(),"addPin");
				msg = "Success fully pinned the question";
				
			}else {
				msg = "You need to login to use this function";
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		return msg;
	}

}
