package stackoverflow.APItester;

import java.io.IOException;

import org.json.JSONException;

import stackoverflow.APIConnecter.QuickSearchResult;

public class QuickSearchResultTest {

	public static void main(String[] args) {
		String title = "title";
		String body = "body";
		String item = "item";
//		String intitle = "Eclipse";
		String intitle = "cccccccccccccccccccccccccccccccc";
		System.out.println("Starting");
		
		try {
			QuickSearchResult s = new QuickSearchResult(intitle);
//			title = s.getTitle();
//			System.out.println(title);
//			body = s.getBody();
//			System.out.println(body);
			if(s.haveResult()) System.out.println("yes");
			else System.out.println("no");
			
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

	
}
}
