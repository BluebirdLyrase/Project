package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.database.SearchingHistoryDatabaseWriter;

public class SearchingWriter extends LocalJsonList{

	public SearchingWriter() throws IOException, JSONException {
		super("SearchingHistory");
	}
	
	public void saveSearchTextHistory(String SearchText,String order,String sort,String site,String tagged) {
		JSONObject newData = new JSONObject();
		try {
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
        ///database///
        //TODO
        //////////////
		}catch (JSONException | IOException e) {
			LOGGER.severe("Error while saving SearchText Histoy : "+e);
			new Log().saveLog(e);
		}
	}

}
