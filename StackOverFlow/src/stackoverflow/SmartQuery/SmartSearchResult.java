package stackoverflow.SmartQuery;

import org.json.JSONArray;
import org.json.JSONException;

public class SmartSearchResult extends SmartAPIConnector {

	private String[] titleList;
	private String[] questionIdList;
	private boolean result = false;
	private String site = "stackoverflow";
	
	public SmartSearchResult(String q) throws JSONException {
		
		String newUrl = "";
		this.json = readJsonFromUrl(newUrl);
		JSONArray resultArray = this.json.getJSONArray("result");
		for(int i = 0;i<resultArray.length();i++) {
//			resultString[i] = resultArray.getString(i);
//			System.out.println(resultString[i]);
			String currentString = resultArray.getString(i).replaceAll("https://","");
			String[] part = currentString.split("/");
			site = part[0].replaceAll(".com"," ");
			questionIdList[i] = part[2];
			questionIdList[i] = part[3].replaceAll("-"," ");
			
			System.out.println(site);
			System.out.println(questionIdList[i]);
			System.out.println(questionIdList[i]);
			result = true;
		}
		
		
	}

	public String[] getTitleList() {
		return titleList;
	}

	public String[] getQuestionIdList() {
		return questionIdList;
	}

	public boolean haveResult() {
		return result;
	}

	public String getSite() {
		return site;
	}
	
}
