package stackoverflow.Tester.manueltest;

import stackoverflow.database.PinnedQuestionWriter;

public class pinnedWriterTest {
	
	public static void main(String[] args) {
		
		//pinText will be insert by user via new Input Dialog 
		new PinnedQuestionWriter().pinnedWriter("10987453", "stackoverflow","How to use Chrome&#39;s network debugger with redirects","Testtt");
		new PinnedQuestionWriter().pinnedWriter("6775257", "stackoverflow","Android Location Providers - GPS or Network Provider?","test2");
		new PinnedQuestionWriter().pinnedWriter("1342", "webmasters","Are there any large web sites written with Python/Django?","test33");
		
	}

}
