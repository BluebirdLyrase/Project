package stackoverflow.Tester;

import java.io.IOException;
import java.net.http.HttpClient;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.database.Account;

public class LoginAPiTest {
	
	public static void main(String[] args) throws JSONException, IOException {   

//		Account a = new Account();
		String i = new Account().Loggin("AmornInw", "yyy", "http://localhost:8095/");
		System.out.println(i);
//		new Account().Logout();//this is how you logout
		
		String x = new Account().getDatabaseURL();
		System.out.println(x);
		String y = new Account().getUserID();
		System.out.println(y);
		
	}

}
