package stackoverflow.Tester.manueltest;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Comment;
import stackoverflow.DataClass.Question;
import stackoverflow.LocalJsonConnector.ContentWriter;

public class ContentTest {

	public static void main(String[] args) throws IOException, JSONException {

		AllContent c = new AllContent("1678122",false,"stackoverflow");
		Question q = c.getAllConetent();
		
		System.out.println("Body"+q.getBody()+"End body");
		System.out.println("Title"+q.getTitle()+"End Title");
		System.out.println("Ownwer"+q.getOwner()+"End Ownwer");
		System.out.println("OwnerImage"+q.getOwnerImage()+"OwnerImage");
		System.out.println("Score"+q.getScore()+"End Score");
		System.out.println("HaveTags"+q.IsHaveTags()+"End HaveTags");
		
		if(q.IsHaveTags()){
			String[] tags = q.getTags();
			for(int i=0;i<tags.length;i++)
				System.out.println(tags[i]);
		}


		if (q.isHaveComment()) {

			Comment[] comment = q.getComment();
			System.out.println(comment.length);

			for (int i = 0; i < comment.length; i++) {
			System.out.print(comment[i].getOwner());
			System.out.println("Comment : "+comment[i].getBody());
			System.out.println(comment[i].getScore());
			System.out.println(comment[i].getOwner());
			
			}
		}

		if (q.isHaveAnswer()) {

			Answer[] a = q.getAnswer();
			for (int i = 0; i < a.length; i++) {
				System.out.println("Loop i : "+i );
				System.out.println(a[i].getBody());
				System.out.println(a[i].getScore());
				System.out.println("Aowner:"+a[i].getOwner());
				System.out.println(a[i].getOwnerImage() );
				
				if (a[i].isHaveComment()) {
					Comment[] comment = a[i].getComment();
					for (int j = 0; j < a[i].getComment().length; j++) {
						System.out.println("Loop j : "+j );
						System.out.println(comment[j].getBody());
					}
				}
			}

		}

	}

}
