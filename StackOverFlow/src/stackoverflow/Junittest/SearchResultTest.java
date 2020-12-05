package stackoverflow.Junittest;

import stackoverflow.APIConnecter.SearchResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;

public class SearchResultTest {
	SearchResult searchResult;
	ArrayList<String> intitles = new ArrayList<String>();
	ArrayList<String> orders = new ArrayList<String>();
	ArrayList<String> tags = new ArrayList<String>();
	ArrayList<String> sort = new ArrayList<String>();
	ArrayList<String> sites = new ArrayList<String>();
	ArrayList<String> expectResult = new ArrayList<String>();
	String actualResult;

	@Before
	public void setUp() throws Exception {
		// searchResult = new SearchResult("Java",1, 40, "asc", "relevance",
		// "stackoverflow", null);
		String csvFile = "csv/SearchResult_TestCase.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			boolean isfirst = true;

			while ((line = br.readLine()) != null) {
				if (isfirst) {
					isfirst = false;
				} else {
					String[] testcase = line.split(cvsSplitBy);
					// index 1 = intitle 2= Order 3=tag 4=sort 5=sites
					intitles.add(testcase[1]);
					orders.add(testcase[2]);
					tags.add(testcase[3]);
					sort.add(testcase[4]);
					sites.add(testcase[5]);
					expectResult.add(testcase[6]);

				}

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Test
	public void haveResultTest() throws IOException, JSONException, InterruptedException {
		String tag;
		for (int i = 0; i < intitles.size(); i++) {
			Thread.sleep(10000);
			tag = tags.get(i);
			if (tag == "null") {
				tag = null;
				System.out.println("tag is null");
			}

			SearchResult actualResult = new SearchResult(intitles.get(i), orders.get(i), sort.get(i), sites.get(i),
					tag);
			assertEquals("round" + i, expectResult.get(i).toLowerCase(),
					actualResult.haveResult().toString().toLowerCase());

		}

	}

	@After
	public void tearDown() throws Exception {
		searchResult = null;
	}

}
