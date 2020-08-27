package stackoverflow.Tester.manueltest;

public class langTest {
	public static void main(String[] args) {
		String z = "English";
		String x = "@$%++";
		String a = "ห";
		System.out.println(z.matches("\\p{Graph}+"));
		System.out.println(x.matches("\\p{Graph}+"));
		System.out.println(a.matches("\\p{Graph}+"));
	}
}
