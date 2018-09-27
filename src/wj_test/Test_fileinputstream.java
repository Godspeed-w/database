package wj_test;

import java.io.FileInputStream;
import java.io.IOException;

public class Test_fileinputstream {
	/**
	 * 测试   1汉字 = 2字节     空格，/t = 1字节   英文字符,数字字符 = 1字节
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("db/aa/copy.txt");
		//编号	姓名	职称	工资	手机号码	所在系编号
		//T017	崔嘉鼎	讲师	4111	18045386734	04
		//读字节， 返回的是字节的十进制表示法
		// ASCII 对照表 http://ascii.911cha.com/
		int a = fis.read();
		System.out.println(a);
		int b = fis.read();
		System.out.println(b);
		int aa = fis.read();
		System.out.println(aa);
		int bb1 = fis.read();
		System.out.println(bb1);
		int bb2 = fis.read();
		System.out.println(bb2);
//		System.out.println(Integer.toHexString(a)+" "+a+" "+Integer.toHexString(b)+" "+b);

		byte[] by = new byte[6];
		//将读到的字节存储到byte数组中，返回的是数组长度
		int d = fis.read(by);	
		System.out.println(d);
		
		for(byte bb : by) {
			System.out.print(bb);			
		}
		System.out.println();
		String c = new String(by, "GBK");
		int cc = by.hashCode();
		
				
		System.out.println(c);
		fis.close();
	}
}
