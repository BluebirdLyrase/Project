package stackoverflow.APIConnecter;

import java.io.IOException;
import org.json.JSONException;

public class SearchResult extends StackOverFlowConnecter {

	String[] titleList;
	String[] questionIdList;
	String item;
	boolean result;

	public SearchResult(String intitle) throws IOException, JSONException {
		this(intitle, 1, 40, "asc", "relevance", "stackoverflow");
	}
	
	public SearchResult(String intitle, int page, int pageSize, String order, String sort, String site)
			throws IOException, JSONException {
		this(intitle, 1, 40, "asc", "relevance", "stackoverflow", null);
	}

	public SearchResult(String intitle, int page, int pageSize, String order, String sort, String site, String tagged)
			throws IOException, JSONException {
		/////Check if there are any tagged
		String tagContent;
		if(tagged == null) {
		tagContent = "";
		}else {
		tagContent = "&tagged=" + tagged;
		}
		
		this.url = "https://api.stackexchange.com/2.2/search/advanced?page=1&pagesize=" + Integer.toString(pageSize)
				+ "&order=" + order + "&sort=" + sort + "&q=" + intitle + "&accepted=True" + tagContent
				+ "&site=stackoverflow&filter=!4(L6lo9D9J9Y3508i";
		
		String newUrl = this.url.replaceAll(" ", "%20");
		this.json = readJsonFromUrl(newUrl);
	}

	public String[] getTitleList() throws JSONException {
		int lenght = json.getJSONArray("items").length();
		titleList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			titleList[i] = json.getJSONArray("items").getJSONObject(i).get("title").toString();
		}
		return titleList;
	}

	public String[] getQuestionIdList() throws JSONException {
		int lenght = json.getJSONArray("items").length();
		questionIdList = new String[lenght];
		for (int i = 0; i < lenght; i++) {
			questionIdList[i] = json.getJSONArray("items").getJSONObject(i).get("question_id").toString();
		}
		return questionIdList;
	}

	//check if there are any result from searching
	public Boolean haveResult() throws JSONException {
		item = json.getJSONArray("items").toString();
		if (item.equals("[]")) {
			LOGGER.warning("There is no result");
			result = false;
		} else
			result = true;
		return result;
	}

}
