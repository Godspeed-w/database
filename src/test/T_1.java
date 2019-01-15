package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import bean.Ram;
import util.Util;

public class T_1 {
	/**
	 * 1、给定某个学生的学号，查询这个学生的相关信息（姓名，性别，所在系名，所在寝室名称）
	 * 
	 * @param args
	 * @throws IOException
	 */
	// 学号： 200901001
	public static void main(String[] args) throws IOException {
		String sno="200901001";
		Util.init();
		boolean flag = true;
		int i = 0, j = 0, num1 = 0, num2 = 0;
		String strA = null, strB = null;
		// 第一次查学生表，找学生姓名，性别
		RandomAccessFile raf1 = new RandomAccessFile("db/学生表.txt", "r");
		while (flag) {
			if (raf1.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						System.out.print(new String(Ram.a, num2, i - 1 - num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("学生表", "sno")]
								.equals(sno)) {
							// strA存储所在系编号
							strA = new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("学生表",
									"depno")];
							// strB存储寝室编号
							strB = new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("学生表",
									"dorno")];
//							Util.fileAppend("temp1", Ram.a,num2,i-1-num2);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("学生表", "sname")].getBytes(), -1, -1);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("学生表", "ssex")].getBytes(), -1, -1);
//							Util.fileAppend("temp1", "\n".getBytes(), -1, -1);
						} else {
							System.out.println("\t" + false);
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
				raf1.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				Util.init();
			}
		}
		raf1.close();
		System.out.println("系编号：" + strA);
		System.out.println("寝室号：" + strB);
		// 第二次查系表：找到通过系编号找到系名
		Util.init();
		flag = true;
		num1 = 0;
		RandomAccessFile raf2 = new RandomAccessFile("db/系表.txt", "r");
		while (flag) {
			System.out.println(raf2.getFilePointer());
			int tt = raf2.read(Ram.a);
			if (tt != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						System.out.print(new String(Ram.a, num2, i - 1 - num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("系表", "depno")]
								.equals(new String(strA))) {

//							Util.fileAppend("temp1", Ram.a,num2,i-1-num2);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("系表", "depname")].getBytes(), -1, -1);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
//							Util.fileAppend("temp1", new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("学生表", "ssex")].getBytes(),-1,-1);
//							Util.fileAppend("temp1", "\n".getBytes(), -1, -1);
						} else {
							System.out.println("\t" + false);
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
				raf2.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				System.out.println("num1:" + num1);
				Util.init();
			}
		}
		raf2.close();
		// 第三次查寝室表：找到通过寝室编号找到寝室
		Util.init();
		flag = true;
		num1 = 0;
		RandomAccessFile raf3 = new RandomAccessFile("db/寝室信息表.txt", "r");
		while (flag) {
			System.out.println(raf3.getFilePointer());
			if (raf3.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						System.out.print(new String(Ram.a, num2, i - 1 - num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("寝室信息表", "dorno")]
								.equals(new String(strB))) {

//							Util.fileAppend("temp1", Ram.a,num2,i-1-num2);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("寝室信息表", "dorname")].getBytes(), -1, -1);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
//							Util.fileAppend("temp1", new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("学生表", "ssex")].getBytes(),-1,-1);
							Util.fileAppend("temp1", "\n".getBytes(), -1, -1);
						} else {
							System.out.println("\t" + false);
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
				raf3.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				System.out.println("num1:" + num1);
				Util.init();
			}
		}
		strA=null;
		strB=null;
		raf2.close();
		System.gc();
		Util.out("temp1");
		File file= new File("db/temp1.txt");
		file.delete();
	}
}
