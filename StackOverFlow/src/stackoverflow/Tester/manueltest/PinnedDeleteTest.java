package stackoverflow.Tester.manueltest;

import java.util.Scanner;

import stackoverflow.database.PinnedQuestionDelete;

public class PinnedDeleteTest {
	
	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			String input = sc.nextLine(); //input is databaseIDList
			String output = new PinnedQuestionDelete().deletePinned(input);
			System.out.println(output);
		}
	}

}
