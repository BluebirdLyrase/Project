package stackoverflow.APItester;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.json.JSONException;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.APIConnecter.AllContentStub;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;

public class ContentTest {

	public static void main(String[] args) throws IOException, JSONException {
//		SearchResult s = new SearchResult("Eclipse");
//		AllContent c = new AllContent("62153976");
//		AllContent c = new AllContent("62153991");
//		AllContent c = new AllContent("1678122");
		AllContentStub c = new AllContentStub("62153976");
		Question q = c.getAllConetent();
		System.out.println(q.getBody());
		System.out.println(q.getTitle());
		


		if (q.isHaveComment()) {

			String[] comment = q.getComment();
			System.out.println(comment.length);

			for (int i = 0; i < comment.length; i++)
				System.out.println(comment[i]);

		}

		if (q.isHaveAnswer()) {

			Answer[] a = q.getAnswer();
			for (int i = 0; i < a.length; i++) {
				System.out.println("Loop i : "+i );
				System.out.println(a[i].getBody());
				System.out.println(a[i].getScore());
				if (a[i].isHaveComment()) {
					String[] comment = a[i].getComment();
					for (int j = 0; j < a[i].getComment().length; j++) {
						System.out.println("Loop j : "+j );
						System.out.println(comment[j]);
					}
				}
			}

		}

	}

}
