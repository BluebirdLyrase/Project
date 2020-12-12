package stackoverflow.SmartQuery;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class test {
	
//	public static void main(String[] args) {
//		String respond = "\"result\":[\r\n" + 
//				"\"https://stackoverflow.com/question/4820716/finding-repeated-words-on-a-strung-and-counting-the-repetitions\",\r\n" + 
//				"\"https://stackoverflow.com/questions/60195430/how-to-create-array-for-text-file-searched-by-user-input\",\r\n" + 
//				"\"https://stackoverflow.com/questions/58312927/assignment-for-sorting-lists\"\r\n" + 
//				"]";
//		
//		String jsonTest = "{"+respond+"}";
//		
//		try {
//			JSONObject json = new JSONObject(jsonTest);
//			JSONArray resultArray = json.getJSONArray("result");
////			String[] resultString = new String[resultArray.length()];
//			String[] questionID = new String[resultArray.length()];
//			String[] title = new String[resultArray.length()];
//			String[] site = new String[resultArray.length()];
//			
//			for(int i = 0;i<resultArray.length();i++) {
////				resultString[i] = resultArray.getString(i);
////				System.out.println(resultString[i]);
//				String currentString = resultArray.getString(i).replaceAll("https://","");
//				String[] part = currentString.split("/");
//				site[i] = part[0].replaceAll(".com"," ");
//				questionID[i] = part[2];
//				title[i] = part[3].replaceAll("-"," ");
//				
//				System.out.println(site[i]);
//				System.out.println(questionID[i]);
//				System.out.println(title[i]);
//						
//			}
////			
////			System.out.println(json.getJSONArray("result").getString(0));
////			System.out.println(json.getJSONArray("result").getString(1));
////			System.out.println(json.getJSONArray("result").getString(2));
//			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void main(String[] args) throws JSONException, ParseException, IOException {
		SmartSearchResult a = new SmartSearchResult("arg util demo println");
	}

}
