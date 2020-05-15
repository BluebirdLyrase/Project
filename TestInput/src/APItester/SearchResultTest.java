package APItester;

import java.io.IOException;

import org.json.JSONException;

import APIConnecter.SearchResult;

public class SearchResultTest {

	public static void main(String[] args) {
		String title = "title";
		String body = "body";
		String intitle = "Eclipse";
		System.out.println("title");
		
		try {
			SearchResult s = new SearchResult(intitle);
			title = s.getTitle();
			body = s.getBody();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		System.out.println(title);
		System.out.println(body);
		
	}

}
