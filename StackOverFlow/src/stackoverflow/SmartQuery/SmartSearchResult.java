package stackoverflow.SmartQuery;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;

public class SmartSearchResult extends SmartAPIConnector {

	private String[] titleList;
	private String[] questionIdList;
	private boolean result = false;
	private String site = "stackoverflow";
	private String q ;
	
	public SmartSearchResult(String q) throws JSONException, ParseException, IOException {
		this.q = q.replaceAll(" ", "%20");;
		String url = "http://117.121.211.193:8000/search?q="+this.q+"&fbclid=IwAR2hQN4Aq-CxMqTBLIhjwVbt77n_fUpW-upNrPSraLstZ0to2IWCGwXr700";
		this.json = readJsonFromUrl(url);
		JSONArray resultArray = this.json.getJSONArray("result");
		int lenght = resultArray.length();
		questionIdList = new String[lenght];
		titleList = new String[lenght];
		
		for(int i = 0;i<lenght;i++) {

			String currentString = resultArray.getString(i).replaceAll("https://","");
			String[] part = currentString.split("/");
			site = part[0].replaceAll(".com"," ");
			questionIdList[i] = part[2];
			titleList[i] = part[3].replaceAll("-"," ");
			
//			System.out.println(site);
//			System.out.println(questionIdList[i]);
//			System.out.println(titleList[i]);
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
