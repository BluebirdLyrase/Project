package stackoverflow.Tester;

import java.io.IOException;
import java.text.DecimalFormat;

import org.json.JSONException;

import stackoverflow.LocalJsonConnector.*;

public class TestGetSize {

	public static void main(String[] args) {
		
		String pattern = "#.###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		try {
			double x1 = new Content().getSize();
			double x2 = new FavoriteWriter().getSize();
			double x3 = new SearchingWriter().getSize();
			double x4 = new ViewWriter().getSize();
			System.out.println("Contnet : " + decimalFormat.format(x1) + "KB" );
			System.out.println("FavoriteWriter : " + decimalFormat.format(x2) + "KB" );
			System.out.println("SearchingWriter : " + decimalFormat.format(x3) + "KB" );
			System.out.println("ViewWriter : " + decimalFormat.format(x4) + "KB" );

		} catch (IOException | JSONException e) {

		}

	}

}
