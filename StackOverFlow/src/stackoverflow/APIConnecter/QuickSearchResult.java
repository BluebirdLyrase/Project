package stackoverflow.APIConnecter;
import java.io.IOException;
import org.json.JSONException;

public class QuickSearchResult extends StackOverFlowConnecter{
	
	String title = "title" ;
	String body = "body" ;	
	String item;
	boolean result = false;
	
	public QuickSearchResult(String intitle) throws IOException, JSONException{
	this.url = "https://api.stackexchange.com/2.2/search?page=1&pagesize=1&order=desc&sort=activity&intitle="+intitle+"&site=stackoverflow&filter=!1zSl_EFEKTmIizC*ys7ti";
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

	public Boolean haveResult() throws JSONException {
	item = json.getJSONArray("items").toString();
	System.out.println(item);
	if(item.equals("[]")) result = false;
	else result = true;	
	return result;
	}
	
	

	

}

