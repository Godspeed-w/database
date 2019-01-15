package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import bean.Ram;
import util.Util;

public class T_9 {
	/**
	 * 9、保密专业的系编号改为08，请完成更改（分三种情况）。
	 * 不允许修改;将系编号置空;级联修改
	 * @param args
	 */
public static void main(String[] args) throws IOException{
	String num="3";
	switch(num) {
	case "1":
		System.out.println("Not allow"); break;
	case "2":
		ArrayList<String> line=Util.selectByFlag("depno,depname,deptel,depmaster", "系表", "depname=保密专业系");
		String insert="\0\0\t";
		for (int i = 1; i < line.size(); i++) {
			insert += line.get(i)+"\t";
		}
		System.out.println(insert);
		File file =new File("db/系表.txt");
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		String temp= null;
		long start=0;
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK");
			long end=raf.getFilePointer();
			if(temp.contains("保密专业系")) {
				System.out.println(temp+"#"+start+"#"+end);
				raf.seek(start);
				raf.write(insert.getBytes());
			}else {
				System.out.println("not "+temp+"#"+start+"#"+end);
			}
			start=end;
		}
		break;
	case "3":
		String orignDepno = Util.selectByFlag("depno", "系表", "depname=保密专业系").get(0);
		String newDepno="##";
		NO3(orignDepno,newDepno);
		break;
	}
}
public static void NO3(String orignDepno,String newDepno) throws IOException {
	//第一次修改系表
	Util.init();
	boolean flag = true;
	int i = 0, j = 0, num1 = 0, num2 = 0,location=0;
	String line=null;
	RandomAccessFile raf = new RandomAccessFile("db/系表.txt", "rw");
	while (flag) {
		if (raf.read(Ram.a) != -1) {
			num2 = 0;
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == '\n') {
					j = i;
					line=new String(Ram.a, num2, i - 1 - num2);
					location=Util.jsonArrayLocation("系表","depno");
					System.out.println(line+"#"+(num1+num2)+"#"+(num1+j-1));
					if(line.split("\t")[location].equals(orignDepno)) {
						int start=num1+num2,end=0;
						for (int k = 0; k < line.split("\t").length; k++) {
							if(location==k) {
								start=(k!=0)?(start+location*("\t".getBytes().length)):(start);
								end=start+line.split("\t")[k].length();
								System.out.println("location:"+location+"start:"+start+" end:"+end);
								raf.seek(start);
								raf.write(newDepno.getBytes());
								System.out.println(raf.getFilePointer());
								break;
							}else {
								start += line.split("\t")[k].length();
								System.out.println("k:"+k+"start:"+start);
							}
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
	//第二次修改学生表
	Util.init();
	flag = true;
	i = 0; j = 0; num1 = 0; num2 = 0;location=0;
	line=null;
	RandomAccessFile raf1 = new RandomAccessFile("db/学生表.txt", "rw");
	while (flag) {
		if (raf1.read(Ram.a) != -1) {
			num2 = 0;
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == '\n') {
					j = i;
					line=new String(Ram.a, num2, i - 1 - num2);
					location=Util.jsonArrayLocation("学生表","depno");
					System.out.println(line+"#"+(num1+num2)+"#"+(num1+j-1));
					if(line.split("\t")[location].equals(orignDepno)) {
						int start=num1+num2,end=0;
						for (int k = 0; k < line.split("\t").length; k++) {
							if(location==k) {
								start=(k!=0)?(start+location*("\t".getBytes().length)):(start);
								end=start+line.split("\t")[k].length();
								System.out.println("location:"+location+"start:"+start+" end:"+end);
								raf1.seek(start);
								raf1.write(newDepno.getBytes());
								System.out.println(raf1.getFilePointer());
								break;
							}else {
								start += line.split("\t")[k].getBytes().length;
								System.out.println("k:"+k+" cond:"+line.split("\t")[k]+ " start:"+start);
							}
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
			raf1.seek(num1 + j + 1);
			num1 = num1 + j + 1;
			Util.init();
		}
	}
	raf1.close();
	//第三次修改教师表
	Util.init();
	flag = true;
	i = 0; j = 0; num1 = 0; num2 = 0;location=0;
	line=null;
	RandomAccessFile raf2 = new RandomAccessFile("db/教师表.txt", "rw");
	while (flag) {
		if (raf2.read(Ram.a) != -1) {
			num2 = 0;
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == '\n') {
					j = i;
					line=new String(Ram.a, num2, i - 1 - num2);
					location=Util.jsonArrayLocation("教师表","depno");
					System.out.println(line+"#"+(num1+num2)+"#"+(num1+j-1));
					if(line.split("\t")[location].equals(orignDepno)) {
						int start=num1+num2,end=0;
						for (int k = 0; k < line.split("\t").length; k++) {
							if(location==k) {
								start=(k!=0)?(start+location*("\t".getBytes().length)):(start);
								end=start+line.split("\t")[k].length();
								System.out.println("location:"+location+"start:"+start+" end:"+end);
								raf2.seek(start);
								raf2.write(newDepno.getBytes());
								System.out.println(raf2.getFilePointer());
								break;
							}else {
								start += line.split("\t")[k].getBytes().length;
								System.out.println("k:"+k+" cond:"+line.split("\t")[k]+ " start:"+start);
							}
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
			raf2.seek(num1 + j + 1);
			num1 = num1 + j + 1;
			Util.init();
		}
	}
	raf2.close();
}
}
