package wj_test;

public class TestRegex {
	public static void main(String[] args) {
		String a="abdc>98,(fdf)>=76";
		String [] b = a.split("\'(',\')'");
		for (String string : b) {
			System.out.println(string);
		}
	}
}
