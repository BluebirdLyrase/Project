package prototype1.APIconnecter;
import java.io.IOException;
import org.json.JSONException;


public class PrototypeData extends StackOverFlowConnecter{
	
	String title ;
	String body ;
	
	public PrototypeData(String url) throws IOException, JSONException {
		this.url = url;
		this.json = readJsonFromUrl(this.url);
	}

	

	
	public String getTitle() throws JSONException{
		title = json.getJSONArray("items").getJSONObject(0).get("title").toString();
		return title;
	}
	
	public String getBody() throws JSONException{
		body =  json.getJSONArray("items").getJSONObject(0).get("body").toString();
		return body;
	}

}
