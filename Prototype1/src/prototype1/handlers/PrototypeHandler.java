package prototype1.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.eclipse.jface.dialogs.MessageDialog;

public class PrototypeHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException{;
		String url = "https://api.stackexchange.com/2.2/questions/60719951?order=desc&sort=activity&site=stackoverflow&filter=!9Z(-wwYGT";
		String title = "title";
		String body = "body";
		try {
			JSONObject json = readJsonFromUrl(url);
			title = json.getJSONArray("items").getJSONObject(0).get("title").toString();
			body =  json.getJSONArray("items").getJSONObject(0).get("body").toString();
		} catch (IOException e) {
		
			e.printStackTrace();
		} catch (JSONException e) {
	
			e.printStackTrace();
		}finally {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(window.getShell(),title,body);
		}
		return null;
		
	}

	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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
