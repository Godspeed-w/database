package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import bean.Ram;
import util.Util;

public class T_10 {
	/**
	 * 10����������ϵҪ����������ɸ��ģ��������������
	 * �������޸�;����������ϵ�����ÿ�;����ɾ��
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
		raf = new RandomAccessFile("db/ϵ��.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			if(!temp.contains("����רҵϵ")) {
				Util.fileAppend("tempϵ��", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/ϵ��.txt");
		fileOld.delete();
		fileNew=new File("db/ϵ��.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/tempϵ��.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		System.out.println("ִ��");
		break;
	case "3":
		String depno = Util.selectByFlag("depno","ϵ��", "depname=����רҵϵ").get(0);
		//����ϵ��
		raf = new RandomAccessFile("db/ϵ��.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			if(!temp.contains("����רҵϵ")) {
				Util.fileAppend("tempϵ��", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/ϵ��.txt");
		fileOld.delete();
		fileNew=new File("db/ϵ��.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/tempϵ��.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		//����ѧ����
		raf = new RandomAccessFile("db/ѧ����.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			if(!temp.split("\t")[Util.jsonArrayLocation("ѧ����", "depno")].equals(depno)) {
				Util.fileAppend("tempѧ����", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/ѧ����.txt");
		fileOld.delete();
		fileNew=new File("db/ѧ����.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/tempѧ����.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		//������ʦ��
		raf = new RandomAccessFile("db/��ʦ��.txt", "rw");
		while((temp=raf.readLine())!=null) {
			temp=new String(temp.getBytes("ISO-8859-1"),"GBK")+"\n";
			System.out.println(temp);
			String split=temp.split("\t")[Util.jsonArrayLocation("��ʦ��", "depno")].split("\n")[0];
			if(!split.equals(depno)) {
				System.out.println(split+"#"+depno);
				System.out.println(split.equals(depno));
				Util.fileAppend("temp��ʦ��", temp.getBytes(), -1, -1);
			}
		}
		fileOld = new File("db/��ʦ��.txt");
		fileOld.delete();
		fileNew=new File("db/��ʦ��.txt");
		fileNew.createNewFile();
		fileOperate=new File("db/temp��ʦ��.txt");
		fileOperate.renameTo(fileNew);
		fileOperate.delete();
		break;
	}
}
}