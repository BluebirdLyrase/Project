package stackoverflow.APItester;

import java.io.IOException;

import org.json.JSONException;

import stackoverflow.APIConnecter.SearchResult;

public class SearchResultTest {

	public static void main(String[] args) throws IOException, JSONException {
		SearchResult s = new SearchResult("Eclipse");
		String[] x = s.getTitleList();
		for(int i=0;i<x.length;i++)
			System.out.println(x[i]);
		
	}

}
