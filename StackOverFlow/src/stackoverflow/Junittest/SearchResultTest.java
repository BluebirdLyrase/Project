package stackoverflow.Junittest;

import stackoverflow.APIConnecter.SearchResult;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.json.JSONException;

public class SearchResultTest {
	

	@Test
	public void haveResultTest() throws IOException, JSONException {
		
		SearchResult s = new SearchResult("Eclipse");
		
		assertNotNull(s.haveResult());
		
	}

}
