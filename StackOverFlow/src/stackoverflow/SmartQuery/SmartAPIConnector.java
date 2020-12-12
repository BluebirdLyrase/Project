package stackoverflow.SmartQuery;

import org.json.JSONException;
import org.json.JSONObject;

public class SmartAPIConnector {

	protected JSONObject json;
	
	protected JSONObject readJsonFromUrl(String url) throws JSONException {
		String respond = "\"result\":[\r\n" + 
				"\"https://stackoverflow.com/question/4820716/finding-repeated-words-on-a-strung-and-counting-the-repetitions\",\r\n" + 
				"\"https://stackoverflow.com/questions/60195430/how-to-create-array-for-text-file-searched-by-user-input\",\r\n" + 
				"\"https://stackoverflow.com/questions/58312927/assignment-for-sorting-lists\"\r\n" + 
				"]";
		String jsonTest = "{"+respond+"}";

		JSONObject resullt = new JSONObject(jsonTest);

		return resullt;
	}
	
}
