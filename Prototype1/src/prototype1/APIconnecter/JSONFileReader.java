package prototype1.APIconnecter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
public class JSONFileReader {
	String location;
	JSONObject json;
	
	protected String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	protected JSONObject readJsonFromFile(String location) throws IOException, JSONException {
		System.out.println("location of file is"+location);
		File file = new File(location);
		InputStream is = new FileInputStream(file);
		
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

}
