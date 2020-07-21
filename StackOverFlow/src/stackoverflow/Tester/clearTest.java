package stackoverflow.Tester;

import java.io.IOException;
import java.util.Scanner;
import org.json.JSONException;
import stackoverflow.LocalJsonConnector.*;

public class clearTest {

	public static void main(String[] args) throws IOException, JSONException {

		Scanner x = new Scanner(System.in);
		String y = x.nextLine();

		switch (y) {
		case "1":new Content().clear();break; //claer Offline Content
		case "2":new FavoriteWriter().clear();break; //Clear FavoriteList
		case "3":new SearchingWriter().clear();break; //Clear SearchHistoryList
		case "4":new ViewWriter().clear();break; //Clear ViewHistoryList
		case "5":new Content().clearAll(); break; //Cleat Every Data
		}

	}

}
