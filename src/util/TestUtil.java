package util;

public class TestUtil {
	//显示数组数据
	public static void showArray(String showDetail,String[] strArray) {
		System.out.println(showDetail);
		for(String str : strArray) {
			System.out.print(str);
		}
		System.out.println();
	}
}
