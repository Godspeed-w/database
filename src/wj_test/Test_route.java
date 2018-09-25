package wj_test;

import java.io.File;

public class Test_route {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
//		File fi = new File("db/");
		File fi = new File("db");
		for(File file : fi.listFiles()){
			System.out.print(file.getName()+" ");
		}
	}

}
