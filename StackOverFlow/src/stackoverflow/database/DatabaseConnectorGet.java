package stackoverflow.database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.APIConnecter.StackExchangeConnecter;
public class DatabaseConnectorGet {
	protected Account account = new Account();
		protected static final Logger LOGGER = Logger.getLogger(StackExchangeConnecter.class.getName());
		protected String url;
		protected JSONObject json;
		
		//convert Reader to String 
		protected String readAll(Reader rd) throws IOException {
			StringBuilder sb = new StringBuilder();
			int cp;
			while ((cp = rd.read()) != -1) {
				sb.append((char) cp);
			}
			return sb.toString();
		}

		//this method will connect to API via url parameter and receive json data
		//then convert it to return in JSONObject form
		protected JSONObject readJsonFromUrl(String apiName) throws IOException, JSONException {
			LOGGER.setLevel(Level.WARNING);
			LOGGER.info("url : "+url);
			String baseURL= account.getDatabaseURL()+"/api/";
			String url = baseURL+apiName;
			InputStream is = new URL(url).openStream();;
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
