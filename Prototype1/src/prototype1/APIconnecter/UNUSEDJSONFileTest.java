package prototype1.APIconnecter;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;

public class UNUSEDJSONFileTest {
	public static void main(String[] args) throws IOException, JSONException {
		SearchResultList s = new SearchResultList("Eclipse");
		String[] x = s.getTitleList();
		for(int i=0;i<x.length;i++)
			System.out.println(x[i]);
	}

}
