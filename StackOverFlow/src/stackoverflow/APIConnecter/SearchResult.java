package stackoverflow.APIConnecter;

import java.io.IOException;
import org.json.JSONException;

/*
 * API : https://api.stackexchange.com/docs/advanced-search#page=1&pagesize=40&order=asc&sort=relevance&q=What&accepted=True&tagged=java%3Bc%2B%2B&filter=!4(L6lo0bkjbSB1w_D&site=stackoverflow&run=true
 * parameter
 * intitle : Search text from UserInputDialog
 * page : page number of result from API, default value is 1 
 * pageSize : Array size of questions from API , default value is 40
 * order : ASC or DESC
 * sort : determine which variable for ordering data
 * site : determine which site in StackExchange to search for
 * tagged : array of tag of the question 
 */

public class SearchResult extends StackExchangeConnecter {

	private String[] titleList;
	private String[] questionIdList;
	private String item;
	private boolean result;
	private String site = "stackoverflow";

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
				+ "&site="+site+"&filter=!4(L6lo9D9J9Y3508i";
		
//		System.out.println("URL : " + this.url) ;
		
		String newUrl = this.url.replaceAll(" ", "%20");
		this.site = site;
		this.json = readJsonFromUrl(newUrl);
		
//		System.out.println(this.json) ;
		
		
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
	
	public String getSite() {
		return this.site;
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
