package stackoverflow.database;
import org.bson.Document;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
public class DatabaseConnector {

	public DatabaseConnector() {
		// TODO Auto-generated constructor stub
	}
	public void databaseWriter() {
		try {
			
			MongoClient  mongo = new  MongoClient ("localhost",27017);
			MongoDatabase database = mongo.getDatabase("StackOverFlowHelper");
			MongoCollection<Document> collection =	database.getCollection("ViewHistory");
			System.out.println(collection.find());
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
