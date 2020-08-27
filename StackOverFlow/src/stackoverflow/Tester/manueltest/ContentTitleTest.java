package stackoverflow.Tester.manueltest;

import java.io.IOException;

import org.json.JSONException;

import stackoverflow.LocalJsonConnector.ContentTitleList;

public class ContentTitleTest {
	
	public static void main(String[] args) throws IOException, JSONException {
		ContentTitleList c = new ContentTitleList();
		int x = c.getLenght();
		String[] filename = c.getFilename();
		String[] title = c.getTitle();
		for(int i = 0;i<x;i++) {
			System.out.println(filename[i]+" : : "+title[i]);
		}
	}

}
