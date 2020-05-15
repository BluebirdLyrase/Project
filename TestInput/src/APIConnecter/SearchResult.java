package APIConnecter;
import java.io.IOException;
import org.json.JSONException;

public class SearchResult extends StackOverFlowConnecter{
	
	String title ;
	String body ;	
	
	public SearchResult(String intitle) throws IOException, JSONException{
	this.url = "https://api.stackexchange.com/2.2/search?pagesize=1&order=desc&sort=activity&intitle="+intitle+"&site=stackoverflow&filter=!9_bDDxJY5";
	this.json = readJsonFromUrl(this.url);
	}

	public String getTitle() throws JSONException {
		title = json.getJSONArray("items").getJSONObject(0).get("title").toString();
		return title;
	}


	public String getBody() throws JSONException {
		body =  json.getJSONArray("items").getJSONObject(0).get("body").toString();
		return body;
	}


	

}
