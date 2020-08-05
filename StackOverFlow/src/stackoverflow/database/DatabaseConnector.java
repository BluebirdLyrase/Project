package stackoverflow.database;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseConnector {
	private String json="";
	private String collection="";
	public void databaseWriter(String json,String collection)throws JSONException, IOException  {
		json="{\"Title\":\"How to use Chrome&#39;s network debugger with redirects\",\"ID\":\"10987453\",\"Tags\":[\"http\",\"google-chrome\",\"google-chrome-devtools\"],\"Date\":\"2020-07-28T18:10:49.284432800\"}";

		try {
			MongoClient mongo = new MongoClient("localhost",27017);
				DBObject obj = (DBObject) com.mongodb.util.JSON.parse(json);
				List<DBObject> listObject = new ArrayList<>();
				listObject.add(obj);
				new MongoClient().getDB("StackOverFlowDB").getCollection(collection).insert(listObject);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
}
