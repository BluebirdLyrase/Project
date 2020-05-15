package APItester;




import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.nio.charset.Charset;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class testapi {
	
	public static void main(String[] args)  throws IOException, JSONException {
		String url = "https://api.stackexchange.com/2.2/questions/60719951?order=desc&sort=activity&site=stackoverflow&filter=!9Z(-wwYGT";
		JSONObject json = readJsonFromUrl(url);
	    System.out.println(json.getJSONArray("items").getJSONObject(0).get("body"));
	    System.out.println(json.getJSONArray("items").getJSONObject(0).get("title"));
		}

	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		    System.out.println(cp);
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	  
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		    InputStream is = new URL(url).openStream();
		    is = new GZIPInputStream(is);
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


