package prototype1.APIconnecter;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONFileReaderTest {
	public static void main(String[] args) throws IOException, JSONException {
		JSONFileReader s = new JSONFileReader();
		TitleListFileReader q = new TitleListFileReader("lib/titleList.json");
//		System.out.print(q.getAll());
		String[] titleList = q.getTitleList();
		for(int i = 0;i<titleList.length;i++) {
			System.out.println(titleList[i]);
		}
	}

}
