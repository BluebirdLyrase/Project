package stackoverflow.APItester;

import java.io.IOException;

import org.json.JSONException;

import stackoverflow.APIConnecter.QuickSearchResult;

public class QuickSearchResultTest {

	public static void main(String[] args) {
		String title = "title";
		String body = "body";
		String intitle = "Eclipse";
		System.out.println("Starting");
		
		try {
			QuickSearchResult s = new QuickSearchResult(intitle);
			title = s.getTitle();
			body = s.getBody();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println(title);
		System.out.println(body);
	
}
}
