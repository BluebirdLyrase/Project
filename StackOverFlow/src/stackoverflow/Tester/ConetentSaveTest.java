package stackoverflow.Tester;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import stackoverflow.APIConnecter.AllContent;
import stackoverflow.DataClass.Question;
import stackoverflow.LocalJsonConnector.ContentTitleWriter;
import stackoverflow.LocalJsonConnector.ContentWriter;

public class ConetentSaveTest {
	
	public static void main(String[] args) throws IOException, JSONException {
//		AllContent c = new AllContent("11227809");
		AllContent c = new AllContent("62153991");
//		AllContent c = new AllContent("1678122");
		Question q = c.getAllConetent();
		
		JSONObject x = c.getJsonObject() ;
		new ContentWriter().saveContent(x,q.getId());
		new ContentTitleWriter().saveContentTitle(q.getTitle(),q.getId());
	}

}
