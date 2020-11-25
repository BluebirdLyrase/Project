package stackoverflow.Junittest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Answer;
import stackoverflow.DataClass.Question;
import stackoverflow.LocalJsonConnector.DefaultDir;
import stackoverflow.LocalJsonConnector.JSONFile;
import stackoverflow.DataClass.Comment;


class TestAllContent {

	@Test
	void testAllContent() throws IOException, JSONException {
		AllContent c = new AllContent("1678122",false,"stackoverflow");
		Question q = c.getAllConetent();
		String title = "&#39;Must Override a Superclass Method&#39; Errors after importing a project into Eclipse";
		String owner = "Tim H";
		String ownerImage = "https://www.gravatar.com/avatar/8eb5110f68d15cce4e0ac76e2a9e246f?s=128&d=identicon&r=PG";
		int score = 1267;
		
		assertNotNull(q.getBody());
		assertEquals(title,q.getTitle());
		assertEquals(owner,q.getOwner());
		assertEquals(ownerImage,q.getOwnerImage());
		assertEquals(score,q.getScore());
		
	}

}
