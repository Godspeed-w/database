package wj_test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Test_read {
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		// TODO Auto-generated method stub
		FileInputStream in = new FileInputStream("db/school/ΩÃ ¶±Ì.txt");
		byte [] readByte = new byte[20];
		int a;
		while((a=in.read(readByte, 0, readByte.length))!=-1) {
			String tt = new String(readByte,"GBK");
			System.out.println(tt);
		}
		in.close();
	}
}
		
