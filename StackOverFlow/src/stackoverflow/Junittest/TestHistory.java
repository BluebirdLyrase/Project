package stackoverflow.Junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import stackoverflow.LocalJsonConnector.ViewHistoryList;

class TestHistory {

	@Test
	void test() throws IOException, JSONException {
		ViewHistoryList s = new ViewHistoryList();
		int x = s.getId().length;
		
		
	}

}
