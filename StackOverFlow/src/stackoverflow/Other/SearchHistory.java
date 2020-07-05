package stackoverflow.Other;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchHistory extends JSONFile {
	
	String[] arrayName = {"SearchTextHistory","ViewHistory"};
	JSONObject jsonObject;
	String filePath = "libs\\newfile.json";
	
	public SearchHistory() throws IOException, JSONException {
		File newFile = new File(filePath);
		if (newFile.createNewFile()) {
			LOGGER.info("[" + LOGGER.getName() + "] " + "File created : " + newFile.getName());
			Files.writeString(Paths.get(filePath), "{"+arrayName[0]+":[],"+arrayName[1]+":[]}", StandardOpenOption.WRITE);
		} else {
			LOGGER.info("[" + LOGGER.getName() + "] " + "File already exists.");
		}
		jsonObject = parseJSONFile(filePath);
	}
		
	
	public void saveSearchTextHistory(String SearchText) throws IOException, JSONException {
		JSONObject newData = new JSONObject();
		newData.put("Search Text", SearchText);
		newData.put("Date",LocalDateTime.now().toString());
		
        JSONArray newArray = jsonObject.getJSONArray(arrayName[0]);
        newArray.put(newData);
        jsonObject.put(arrayName[0],newArray);
        saveJSONFile(filePath, jsonObject);
	}
	
	public void saveViewHistory(String SearchText,String[] tags) throws JSONException, IOException {
		JSONObject newData = new JSONObject();
		newData.put("Search Text", SearchText);
		JSONArray arrayTags = new JSONArray();
		for(int i = 0;i<tags.length;i++) {
			arrayTags.put(tags[i]);
		}
		newData.put("Tags",arrayTags);
		newData.put("Date",LocalDateTime.now().toString());
		
        JSONArray newArray = jsonObject.getJSONArray(arrayName[1]);
        newArray.put(newData);
        jsonObject.put(arrayName[1],newArray);
        saveJSONFile(filePath, jsonObject);
	}
	


}
