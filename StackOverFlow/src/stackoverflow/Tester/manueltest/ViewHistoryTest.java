package stackoverflow.Tester.manueltest;

import java.io.IOException;

import org.json.JSONException;

import stackoverflow.LocalJsonConnector.ViewHistoryList;

public class ViewHistoryTest {
	public static void main(String[] args) throws IOException, JSONException {
		ViewHistoryList s = new ViewHistoryList();
		int x = s.getId().length;
		
		String[] id = s.getId();
		String[] date = s.getViewDate();
		String[] tags = s.getTags();
		String[] title = s.getTitle();
		System.out.println("test");
		for(int i = 0;i<x;i++) {
			System.out.println(id[i]+" : : "+date[i]+" : : "+tags[i]+" : : "+title[i]);
		}
		
	}
}
