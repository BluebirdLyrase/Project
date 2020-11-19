package stackoverflow.junittest;

import org.junit.Test;

import stackoverflow.APIConnecter.SearchResult;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.json.JSONException;



public class TestUnitest {

	   String message = "Hello World";	
	   MessageUtil messageUtil = new MessageUtil(message);
	
 @Test
 public void testPrintmessage() throws IOException, JSONException {
	 
	 SearchResult s = new SearchResult("Eclipse");
	 
	 String[] x = s.getTitleList();
	 String[] y = s.getQuestionIdList();
	 

	 
	 
	 assertEquals(message,messageUtil.printMessage());
	 
 }

}
