package stackoverflow.Junittest;

import stackoverflow.database.Account;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestLogin {
	Account account;
	ArrayList<String> user_ID = new ArrayList<String>();
	ArrayList<String> user_Password = new ArrayList<String>();
	ArrayList<String> url = new ArrayList<String>();
	ArrayList<String> expectResult = new ArrayList<String>();
	String actualResult;

	@Before
	public void setUp() throws Exception {
		String csvFile = "csv/Login_TestCase.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		System.out.print("BrRasdawd");
		try {
			br = new BufferedReader(new FileReader(csvFile));
			boolean isfirst = true;
			
			while ((line = br.readLine()) != null) {
				if (isfirst) {
					isfirst = false;
				} else {
					String[] testcase = line.split(cvsSplitBy);
					// index 1 = ID 2= Password 3=url 4=result
					System.out.print(testcase[1]);
					user_ID.add(testcase[1]);
					user_Password.add(testcase[2]);
					url.add(testcase[3]);
					expectResult.add(testcase[4]);

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
	public void test() throws InterruptedException, IOException, JSONException {
		String id;
		String passwrd;
		System.out.print(user_ID.size());
		for (int i = 0; i < user_ID.size(); i++) {
			Thread.sleep(1000); 
			id = user_ID.get(i);
			passwrd = user_Password.get(i);
			if (id == "null") {
				id = "";
				System.out.println("ID is null");
			}
			if (passwrd == "null") {
				passwrd = "";
				System.out.println("Password is null");
			}
			
			Account actualResult_obj = new Account();
			
			 
			actualResult = actualResult_obj.Loggin(id, passwrd, url.get(i));
			System.out.print(actualResult);
			assertEquals("round" + i, expectResult.get(i).toLowerCase(), actualResult.toString().toLowerCase());
		}
	}

	@After
	public void tearDown() throws Exception {
		account = null;
	}

}
