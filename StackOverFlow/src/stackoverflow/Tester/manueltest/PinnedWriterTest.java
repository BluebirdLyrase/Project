package stackoverflow.Tester.manueltest;

import stackoverflow.database.PinnedQuestionWriter;

public class PinnedWriterTest {
	
	public static void main(String[] args) {
		
		//pinText will be insert by user via new Input Dialog 
		String x = new PinnedQuestionWriter().pinnedWriter("10987453", "stackoverflow","How to use Chrome&#39;s network debugger with redirects","Testtt");
		String x2 = new PinnedQuestionWriter().pinnedWriter("6775257", "stackoverflow","Android Location Providers - GPS or Network Provider?","test2");
		String x3 =  new PinnedQuestionWriter().pinnedWriter("1342", "webmasters","Are there any large web sites written with Python/Django?","test33");
		System.out.println(x+"\n"+x2+"\n"+x3+"\n");
	}

}
