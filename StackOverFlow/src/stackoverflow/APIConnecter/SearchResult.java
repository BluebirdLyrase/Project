package stackoverflow.APIConnecter;
import java.io.IOException;
import org.json.JSONException;

public class SearchResult extends StackOverFlowConnecter{

	String[] titleList;
	String[] questionIdList;
	String item;
	Boolean result;
	
	public SearchResult(String intitle) throws IOException, JSONException {
		this.url = "https://api.stackexchange.com/2.2/search/advanced?pagesize=20&order=asc&sort=relevance&q="+intitle+"&site=stackoverflow&filter=!)5IW-1CBLPytOiimbWji6k(KM(r5";
		String newUrl = this.url.replaceAll(" ", "%20");
		this.json = readJsonFromUrl(newUrl);
	}

	public String[] getTitleList() throws JSONException {
		int lenght = json.getJSONArray("items").length();
		String[] titleList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			titleList[i] = json.getJSONArray("items").getJSONObject(i).get("title").toString();
		}
		return titleList;
	}

	public String[] getQuestionIdList() throws JSONException{
		int lenght = json.getJSONArray("items").length();
		String[] questionIdList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			questionIdList[i] = json.getJSONArray("items").getJSONObject(i).get("question_id").toString();
		}
		return questionIdList;
	}
	
	public Boolean haveResult() throws JSONException {
	item = json.getJSONArray("items").toString();
	if(item.equals("[]")) {
		LOGGER.warning("There is no result");
		result = false;
	}
	else result = true;	
	return result;
	}
	
	

}

