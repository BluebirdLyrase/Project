package stackoverflow.Tester;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import stackoverflow.database.DatabaseConnector;
import stackoverflow.database.UserDatabaseWriter;
import stackoverflow.database.ViewHistoryDatabaseWriter;

import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.CollectibleCodec;
import org.json.JSONException;

public class getDatabaseTest {


	public getDatabaseTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String args[]) throws JSONException, IOException {
		/*
		 * String collection; collection="ViewHistory"; String json = null;
		 * json="{\"Title\":\"How to use Chrome&#39;s network debugger with redirects\",\"ID\":\"10987453\",\"Tags\":[\"http\",\"google-chrome\",\"google-chrome-devtools\"],\"Date\":\"2020-07-28T18:10:49.284432800\"}"
		 * ; MongoClient mongoClient = new MongoClient("localhost",27017);
		 * 
		 * DBObject obj = (DBObject) com.mongodb.util.JSON.parse(json); List<DBObject>
		 * listObject = new ArrayList<>(); listObject.add(obj);
		 * mongoClient.getDB("StackOverFlowDB").getCollection(collection).insert(
		 * listObject);
		 */
		
//		ViewHistoryDatabaseWriter dbcon = new ViewHistoryDatabaseWriter("\"How to use Chrome&#39;s network debugger with redirects\"","\"10987453\"","\"http\",\"google-chrome\",\"google-chrome-devtools\"","\"2020-07-28T18:10:49.284432800\"");

	
	   } 
		
	}

