package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchingWriter extends Searching{

	public SearchingWriter() throws IOException, JSONException {
		super();
	}
	
	public void saveSearchTextHistory(String SearchText,String order,String sort,String site,String tagged) throws IOException, JSONException {
		JSONObject newData = new JSONObject();
		newData.put("Search Text", SearchText);
		newData.put("Order", order);
		newData.put("Sort By", sort);
		newData.put("Site", site);
		newData.put("Tagged", tagged);
		newData.put("Date",LocalDateTime.now().toString());
		
        JSONArray newArray = jsonObject.getJSONArray(arrayName);
        newArray.put(newData);
        jsonObject.put(arrayName,newArray);
        saveJSONFile(filePath, jsonObject);
	}

}
