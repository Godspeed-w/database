package wj_test;

import java.io.File;

public class Test_route {

	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
//		File fi = new File("db/");
		File fi = new File("db");
		for(File file : fi.listFiles()){
			System.out.print(file.getName()+" ");
		}
	}

}
