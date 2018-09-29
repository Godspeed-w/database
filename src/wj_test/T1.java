package wj_test;

import java.io.File;
import java.io.IOException;

import bean.TableConfig;

public class T1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TableConfig tf = new TableConfig();
		System.out.println(tf.toString());
		String str = "LastName char(255),";
		System.out.println(str.indexOf("char1"));
		System.out.println(str.contains(str));
		
		File fl = new File("db/aa/copy123.txt");
//		if(fl.exists() ) {
//			System.out.println("this table alrady exist");
//		}else {
//			System.out.println("none");
//		}
		fl.createNewFile();
		String a,b,c,d;
		String ss = 
		"{"+
			"\"col_name\":\"" + a + "\"," +
			"\"type\":\"" + a + "\"," +
			"\"size\":\"" + a + "\"," +
			"\"isNull\":\"" + a + "\"," +
		"}";
	
}
