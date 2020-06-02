package stackoverflow.APItester;

import java.io.IOException;

import org.json.JSONException;

import stackoverflow.APIConnecter.SearchResult;

public class SearchResultTest {

	public static void main(String[] args) throws IOException, JSONException {
//		SearchResult s = new SearchResult("Eclipse");
		SearchResult s = new SearchResult("Eclipseccccccccccccccccccc");
		if(s.haveResult()) {
		String[] x = s.getTitleList();
		String[] y = s.getQuestionIdList();
		for(int i=0;i<x.length;i++) {
			System.out.println(x[i]);
			System.out.println(y[i]);
		}
		}else {
			System.out.print("no result");
		}
	}

}
