package prototype1.APIconnecter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.json.JSONException;
import org.osgi.framework.Bundle;

public class UNUSEDSearchResultReader extends UNUSEDJSONFileReader {

	public UNUSEDSearchResultReader() throws IOException, JSONException {
		this.location = "lib/titleList.json";
		Bundle bundle = Platform.getBundle("Prototype1");
		URL fileURL = bundle.getEntry(location);
		File file = null;
		try {
		    file = new File(FileLocator.resolve(fileURL).toURI());
			this.json = readJsonFromFile(file);
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		
		
		System.out.println("heyyyyy"+fileURL);

	}
	
	public String[] getTitleList() throws JSONException {
		int titleLenght = json.getJSONArray("items").length();
		String[] titleList = new String[titleLenght];

		for (int i = 0; i < titleLenght; i++) {
			titleList[i] = json.getJSONArray("items").getJSONObject(i).get("title").toString();
		}

		return titleList;
	}
	
	public String getAll() {
		return json.toString();
	}

}
