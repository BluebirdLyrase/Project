package stackoverflow.APIConnecter;
import java.io.BufferedReader;
import java.util.logging.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.json.JSONException;
import org.json.JSONObject;
/*
This class is a parent class for all class in stackoverflow.APIConecter package
*/
public class StackExchangeConnecter {
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
	protected JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		LOGGER.setLevel(Level.WARNING);
		LOGGER.info("url : "+url);
		JSONObject result = null;
		URL myUrl = new URL(url);
		URLConnection con = myUrl.openConnection();
		con.setConnectTimeout(10000);
		con.setReadTimeout(10000);
		InputStream is = con.getInputStream();
		
//		//Encryption
		is = new GZIPInputStream(is);
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF8")));
			String jsonText = readAll(rd);
			result = new JSONObject(jsonText);
		} finally {
			is.close();
		}
		return result;
	}

}

