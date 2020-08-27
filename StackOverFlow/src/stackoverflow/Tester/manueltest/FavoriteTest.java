package stackoverflow.Tester.manueltest;
import java.io.IOException;

import org.json.JSONException;

import stackoverflow.LocalJsonConnector.FavoriteList;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
public class FavoriteTest {
	
	public static void main(String[] args) throws IOException, JSONException {
		new FavoriteWriter().saveFavorite("aaa", "123");
		new FavoriteWriter().saveFavorite("bbb", "456");
		new FavoriteWriter().saveFavorite("ccc", "789");
		new FavoriteWriter().saveFavorite("ccc", "789");
		
		FavoriteList f = new FavoriteList();
		int x= f.getLenght();
		
		String[] id = f.getID();
		String[] title = f.getTitle();
		for(int i = 0;i<x;i++) {
			System.out.println(id[i]+" : : "+title[i]);
		}
		
	}

}
