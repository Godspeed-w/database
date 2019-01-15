package test;

import java.io.IOException;
import java.io.RandomAccessFile;

import bean.Ram;
import util.Util;

public class T_readFile {
	public static void main(String[] args) throws IOException {
		Util.init();
		boolean flag=true;
		int i=0,j=0,num1=0,num2=0;
		String strA=null,strB = null;
		RandomAccessFile raf = new RandomAccessFile("db/系表.txt", "r");
		while (flag) {
			if (raf.read(Ram.a) != -1) {
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
					}
				}
				//此处没有去掉行，所以不加ln
				System.out.print(new String(Ram.a, 0, j));
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf.seek(num1 + j);
				num1 = num1 + j;
				Util.init();
			} 
		}
		raf.close();
	}
}
