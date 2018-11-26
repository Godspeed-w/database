package wj_test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Test_read {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// TODO Auto-generated method stub
		FileInputStream in = new FileInputStream("db/school/ΩÃ ¶±Ì.txt");
		byte [] readByte = new byte[400];
		int a;
		a=in.read(readByte, 0, readByte.length);
//		while((a=in.read(readByte, 0, readByte.length))!=-1) {
			String tt = new String(readByte,"GBK");
			System.out.print(tt);
//		}
		in.close();
	}
}
		
