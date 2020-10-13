package stackoverflow.Tester.manueltest;

import org.json.JSONException;

import stackoverflow.database.PinnedQuestionList;

public class PinnedQuestionListTester {
	
	public static void main(String[] args) throws JSONException {
		PinnedQuestionList x = new PinnedQuestionList();
		for(int i = 0;i < x.getLenght();i++) {
		System.out.println(x.getTitleList()[i]);
		System.out.println(x.getOwnerID()[i]);
		System.out.println(x.getPinText()[i]);
		System.out.println(x.getQuestionIdList()[i]);
		System.out.println(x.getSiteList()[i]);
		System.out.println(x.getTitleList()[i]);
		System.out.println(x.getDatabaseIdList()[i]);
		}
	}

}
