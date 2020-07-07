package stackoverflow.Tester;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.LocalJsonConnector.SearchHistory;

public class JsonReadFormFile {

	public static void main(String arg[]) throws JSONException, IOException {
		
		SearchHistory x = new SearchHistory();
		x.saveSearchTextHistory("How to use viewpart");
		x.saveViewHistory("How to use viewpart", new String[]{"java","eclipse"},"How to use Viewpart in Eclipse");
		x.saveViewHistory("How to use viewpart", new String[]{"java","c++"},"How to bake a cake");
		x.saveViewHistory("How to use viewpart", new String[]{"java","southpark"},"OH MY GOD! They Kill Kenny!");
		x.saveViewHistory("How to use viewpart", new String[]{"southpark"},"YOU Basterds!");
		
		
//        String filename = "libs\\content.json";
//        JSONObject jsonObject = parseJSONFile(filename);
//        System.out.println(jsonObject.toString());
        

//		replace(":", "-").replace(".", "").replace("-", "");
		
//		String newfilename = "libs\\newfile.json";
//		File myFile = new File(newfilename);
//		
//	      if (myFile.createNewFile()) {
//	          System.out.println("File created: " + myFile.getName());
//	          Files.writeString(Paths.get(newfilename), "{Collection:[]}", StandardOpenOption.WRITE);
//	        } else {
//	          System.out.println("File already exists.");
//	        }
//	      
//        
//        JSONObject row = new JSONObject();
//        row.put("date",LocalDateTime.now().toString());
//        row.put("search text","I'm stupid plz help");
//        row.put("tag","java,c++");
//        
//        JSONObject row2 = new JSONObject();
//        row2.put("date",LocalDateTime.now().toString());
//        row2.put("search text","I'm stupid plz help");
//        row2.put("tag","java,c++");
//        
//        JSONObject n = parseJSONFile(newfilename);
//        JSONArray a = n.getJSONArray("Collection");
//        a.put(row);
//        a.put(row2);
//        
//        n.put("Collection", a);
//        
//        System.out.println(n.toString());  
////        Just convert your JSONObject to string and write it like 
//        Files.writeString(Paths.get(newfilename), n.toString(), StandardOpenOption.WRITE);
        
        
        
	}


	

	public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return new JSONObject(content);
    }
	
}
