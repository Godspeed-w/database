package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import bean.Ram;
import util.Util;

public class T_10 {
	/**
	 * 10、电子商务系要撤消，请完成更改（分三种情况）。
	 * 不允许修改;将电子商务系整行置空;级联删除
	 * @param args
	 * @throws IOException 
	 */
public static void main(String[] args) throws IOException {
	String num="3";
	RandomAccessFile raf=null;
	String temp= null;
	File fileOld =null;
	File fileOperate=null;
	File fileNew=null;
	switch(num) {
	case "1":
		System.out.println("Not allow"); break;
	case "2":
		raf = new RandomAccessFile("db/系表.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			if(!temp.contains("保密专业系")) {
				Util.fileAppend("temp系表", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/系表.txt");
		fileOld.delete();
		fileNew=new File("db/系表.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/temp系表.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		System.out.println("执行");
		break;
	case "3":
		String depno = Util.selectByFlag("depno","系表", "depname=保密专业系").get(0);
		//操作系表
		raf = new RandomAccessFile("db/系表.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			if(!temp.contains("保密专业系")) {
				Util.fileAppend("temp系表", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/系表.txt");
		fileOld.delete();
		fileNew=new File("db/系表.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/temp系表.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		//操作学生表
		raf = new RandomAccessFile("db/学生表.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			if(!temp.split("\t")[Util.jsonArrayLocation("学生表", "depno")].equals(depno)) {
				Util.fileAppend("temp学生表", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/学生表.txt");
		fileOld.delete();
		fileNew=new File("db/学生表.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/temp学生表.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		//操作教师表
		raf = new RandomAccessFile("db/教师表.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			String split=temp.split("\t")[Util.jsonArrayLocation("教师表", "depno")].split("\n")[0];
			if(!split.equals(depno)) {
				System.out.println(split+"#"+depno);
				System.out.println(split.equals(depno));
				Util.fileAppend("temp教师表", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/教师表.txt");
		fileOld.delete();
		fileNew=new File("db/教师表.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/temp教师表.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		break;
	}
}
}