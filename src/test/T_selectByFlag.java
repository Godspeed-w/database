package test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import bean.Ram;
import util.Util;

public class T_selectByFlag {
	/**
	 * select sname,ssex,depno,dorno from 学生表 where sno=200906093;
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static ArrayList<String> selectByFlag(String flags, String tableName, String condition) throws IOException {
		Util.init();
		boolean flag = true;
		int i = 0, j = 0, num1 = 0, num2 = 0;
		ArrayList<String> list = new ArrayList<String>();
		RandomAccessFile raf = new RandomAccessFile("db/" + tableName + ".txt", "r");
		while (flag) {
			if (raf.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
//						System.out.println(new String(Ram.a,num2,i-1-num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation(tableName,
								condition.split("=")[0])].equals(condition.split("=")[1])) {
							for (int k = 0; k < flags.split(",").length; k++) {
								list.add(new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
										.jsonArrayLocation(tableName, flags.split(",")[k])]);
							}
						}
						num2 = i + 1;
					}
				}
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				Util.init();
			}
		}
		raf.close();
		return list;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(selectByFlag("stel,sname,ssex,depno,dorno", "学生表", "sno=200906093").toString());
	}
}
