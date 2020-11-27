package stackoverflow.Junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import stackoverflow.LocalJsonConnector.FavoriteList;
import stackoverflow.LocalJsonConnector.FavoriteWriter;

class TestFavorite {

		@Test
		void test() throws IOException, JSONException {
			new FavoriteWriter().saveFavorite("Why is processing a sorted array faster than processing an unsorted array?", 
					"11227809","stackoverflow");
			FavoriteList f = new FavoriteList();
			
			int x = f.getLenght();
			String[] id = f.getID();
			String[] title = f.getTitle();
			
			assertEquals(id[x-1],"11227809");
			assertEquals(title[x-1],"Why is processing a sorted array faster than processing an unsorted array?");
			
		}

	}



