package stackoverflow.LocalJsonConnector;

import java.io.IOException;
import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchingWriter extends LocalJsonList{

	public SearchingWriter() throws IOException, JSONException {
		super("SearchingHistory");
	}
	
	public void saveSearchTextHistory(String SearchText,String order,String sort,String site,String tagged) {
		JSONObject newData = new JSONObject();
		try {
			System.out.print(SearchText);
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
		}catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			LOGGER.severe("[" + LOGGER.getName() + "] " + "Error while saving SearchText Histoy : "+e);
			e.printStackTrace();
		}
	}

}
