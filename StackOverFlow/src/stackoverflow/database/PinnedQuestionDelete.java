package stackoverflow.database;

import org.json.JSONException;

import stackoverflow.LocalJsonConnector.Log;

public class PinnedQuestionDelete {
	private String userID ;
	private String msg = "";
	public String deletePinned() {
		Account account = new Account();
		
		try {
			this.userID = account.getUserID();
			if (account.isLoggedIn()) {
				
				
			}else {
				
				
			}
		} catch (JSONException e) {
			new Log().saveLog(e);
			e.printStackTrace();
		}
		
		
		return msg;
	}

}
