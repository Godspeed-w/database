package test;

import java.io.IOException;
import java.io.RandomAccessFile;

import bean.Ram;
import util.Util;

public class T_readFile_deleteN {
	public static void main(String[] args) throws IOException {
		Util.init();
		boolean flag=true;
		int i=0,j=0,num1=0,num2=0;
		String strA=null,strB = null;
		RandomAccessFile raf = new RandomAccessFile("db/寝室信息表.txt", "r");
		while (flag) {
			if (raf.read(Ram.a) != -1) {
				num2=0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						//输出取消ln输出为一行，证明去掉换行
						System.out.print(new String(Ram.a,num2,i-1-num2));
						num2=i+1;
					}
				}
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf.seek(num1 + j+1);
				num1 = num1 + j+1;
				Util.init();
			} 
		}
		raf.close();
	}
}
