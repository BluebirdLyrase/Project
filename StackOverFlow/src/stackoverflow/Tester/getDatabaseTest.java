package stackoverflow.Tester;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoCredential;
import com.mongodb.MongoClientOptions;


import java.util.Arrays;

import org.bson.codecs.CollectibleCodec;

public class getDatabaseTest {

	public getDatabaseTest() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String args[]) {
		
		try {
		MongoClient mongo = new MongoClient("localhost",27017);
		mongo.getDatabaseNames().forEach(System.out::println);


	
		
		}catch(Exception e){
			
			e.printStackTrace();
		}
		
	
	   } 
		
	}

