package stackoverflow.Junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.DecimalFormat;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import stackoverflow.LocalJsonConnector.Content;
import stackoverflow.LocalJsonConnector.FavoriteWriter;
import stackoverflow.LocalJsonConnector.SearchingWriter;
import stackoverflow.LocalJsonConnector.ViewWriter;

class TestSetting {

	@Test
	void test() throws IOException, JSONException {

		double x1 = new Content().getSize();
		double x2 = new FavoriteWriter().getSize();
		double x3 = new SearchingWriter().getSize();
		double x4 = new ViewWriter().getSize();

		assertNotNull(x1);
		assertNotNull(x2);
		assertNotNull(x3);
		assertNotNull(x4);

		
	}

}
