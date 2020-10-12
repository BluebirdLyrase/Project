package stackoverflow.APIConnecter;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class AllContentObjectOnly extends StackExchangeConnecter{
	
	//return JsonObject for save to Offline function in every tableview
	public JSONObject getJsonObject(String question_id,String site) throws IOException, JSONException {
		
		this.url = "https://api.stackexchange.com/2.2/questions/" + question_id
				+ "?order=asc&sort=activity&site="+site+"&filter="
				+ "!6CZol-kjk43Caeu4wbmgfWPFBKTl-6MgX9_mx25H6._QEcG9r2lN3QrdeDe";
		this.json = readJsonFromUrl(this.url);
		return this.json;
		
	}
}
