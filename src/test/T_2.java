package test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import bean.Ram;
import util.Util;

public class T_2 {
	/**
	 * 2������ĳ��ѧ����ѧ�ţ���ѯѡ������λѧ��ȫ���γ̵�ѧ����Ϣ��
	 * @param args
	 * @throws IOException 
	 */
	// 200906087
public static void main(String[] args) throws IOException {
	String searchSno="200904037";
	Util.init();
	boolean flag=true;
	int i=0,j=0,num1=0,num2=0;
	ArrayList<String> list1 = new ArrayList<String>();
	ArrayList<String> list2 = new ArrayList<String>();
	//��һ�β�ѧ��ѡ�α��Ҹ�ѧ��ѡ�����пγ�,����temp2_1.txt��
	RandomAccessFile raf1 = new RandomAccessFile("db/ѧ��ѡ�α�.txt", "r");
	while (flag) {
		if (raf1.read(Ram.a) != -1) {
			num2=0;
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == '\n') {
					j = i;
//					System.out.println(new String(Ram.a,num2,i-1-num2));
					if(new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "sno")].equals(searchSno)){
						Util.fileAppend("temp2_1", new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "cno")].getBytes(),-1,-1);
						Util.fileAppend("temp2_1", "\n".getBytes(), -1, -1);
						list1.add(new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "cno")]);
					}
					num2=i+1;
				}
			}
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == 0) {
					flag = false;
					break;
				}
			}
			raf1.seek(num1 + j+1);
			num1 = num1 + j+1;
			Util.init();
		} 
	}
	System.out.println("��ͬѧѡ�Ŀγ��У�"+list1.toString());
	int listNum=list1.size();
	raf1.close();
	//�ڶ��β�ѧ��ѡ�α���ѡ�˸�ѧ��ѡ�Ŀγ̵�ѧ��,����temp2_2.txt��
	Util.init();
	flag=true;
	num1=0;
	RandomAccessFile raf2 = new RandomAccessFile("db/ѧ��ѡ�α�.txt", "r");
	while (flag) {
		if (raf2.read(Ram.a) != -1) {
			num2=0;
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == '\n') {
					j = i;
//					System.out.print(new String(Ram.a,num2,i-1-num2));
					String str=new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "cno")];
//					System.out.println(" #"+str);
					String tno=null;
					if(list1.contains(str)){
						String sno=new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "sno")];
						String cno=new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "cno")];
						String grade=new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ��ѡ�α�", "grade")];
						Util.fileAppend("temp2_2", sno.getBytes(),-1,-1);
						Util.fileAppend("temp2_2", "\t".getBytes(), -1, -1);
						Util.fileAppend("temp2_2", cno.getBytes(),-1,-1);
						Util.fileAppend("temp2_2", "\t".getBytes(), -1, -1);
						Util.fileAppend("temp2_2", grade.getBytes(),-1,-1);
						Util.fileAppend("temp2_2", "\n".getBytes(), -1, -1);
						list2.add(sno+"#"+cno);
					}
					num2=i+1;
				}
			}
			for (i = 0; i < Ram.a.length; i++) {
				if (Ram.a[i] == 0) {
					flag = false;
					break;
				}
			}
			raf2.seek(num1 + j+1);
			num1 = num1 + j+1;
			Util.init();
		} 
	}
	Collections.sort(list2, new Comparator< String>() {
		@Override
		public int compare(String o1, String o2) {
			// TODO Auto-generated method stub
			if(o1.compareTo(o2)>0) {
				return 	1;
			}else {
				return -1;
			}
		}
	});
	for (String string : list2) {
		System.out.println(string);
	}
	int list1OriginSize=list1.size();
	int times=0;
	for (int k = 0; k < list2.size(); k++) {
		if(k==list2.size()-1) {
			break;
		}
		if(list2.get(k).split("#")[0].equals(list2.get(k+1).split("#")[0])) {
			times++;
			if(times==list1OriginSize-1) {
				list1.add(list2.get(k).split("#")[0]);
				times=0;
			}
		}else {
			times=0;
		}
	}
	System.out.println(list1.toString());
	//��ȡlist1 4λ֮������ݣ�ѭ�����õ�һ�ⷽ��
	//������
	String response="";
	for (int k = listNum; k < list1.size(); k++) {
		response += list1.get(k)+"\n";
	}
	list1.clear();
	list2.clear();
	raf2.close();
	System.gc();
	System.out.println(response);
}
}
