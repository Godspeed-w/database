package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import bean.Ram;
import util.Util;

public class T_1 {
	/**
	 * 1������ĳ��ѧ����ѧ�ţ���ѯ���ѧ���������Ϣ���������Ա�����ϵ���������������ƣ�
	 * 
	 * @param args
	 * @throws IOException
	 */
	// ѧ�ţ� 200901001
	public static void main(String[] args) throws IOException {
		String sno="200901001";
		Util.init();
		boolean flag = true;
		int i = 0, j = 0, num1 = 0, num2 = 0;
		String strA = null, strB = null;
		// ��һ�β�ѧ������ѧ���������Ա�
		RandomAccessFile raf1 = new RandomAccessFile("db/ѧ����.txt", "r");
		while (flag) {
			if (raf1.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						System.out.print(new String(Ram.a, num2, i - 1 - num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("ѧ����", "sno")]
								.equals(sno)) {
							// strA�洢����ϵ���
							strA = new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("ѧ����",
									"depno")];
							// strB�洢���ұ��
							strB = new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("ѧ����",
									"dorno")];
//							Util.fileAppend("temp1", Ram.a,num2,i-1-num2);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("ѧ����", "sname")].getBytes(), -1, -1);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("ѧ����", "ssex")].getBytes(), -1, -1);
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
		System.out.println("ϵ��ţ�" + strA);
		System.out.println("���Һţ�" + strB);
		// �ڶ��β�ϵ���ҵ�ͨ��ϵ����ҵ�ϵ��
		Util.init();
		flag = true;
		num1 = 0;
		RandomAccessFile raf2 = new RandomAccessFile("db/ϵ��.txt", "r");
		while (flag) {
			System.out.println(raf2.getFilePointer());
			int tt = raf2.read(Ram.a);
			if (tt != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						System.out.print(new String(Ram.a, num2, i - 1 - num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("ϵ��", "depno")]
								.equals(new String(strA))) {

//							Util.fileAppend("temp1", Ram.a,num2,i-1-num2);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("ϵ��", "depname")].getBytes(), -1, -1);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
//							Util.fileAppend("temp1", new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ����", "ssex")].getBytes(),-1,-1);
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
		// �����β����ұ��ҵ�ͨ�����ұ���ҵ�����
		Util.init();
		flag = true;
		num1 = 0;
		RandomAccessFile raf3 = new RandomAccessFile("db/������Ϣ��.txt", "r");
		while (flag) {
			System.out.println(raf3.getFilePointer());
			if (raf3.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						System.out.print(new String(Ram.a, num2, i - 1 - num2));
						if (new String(Ram.a, num2, i - 1 - num2).split("\t")[Util.jsonArrayLocation("������Ϣ��", "dorno")]
								.equals(new String(strB))) {

//							Util.fileAppend("temp1", Ram.a,num2,i-1-num2);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
							Util.fileAppend("temp1", new String(Ram.a, num2, i - 1 - num2).split("\t")[Util
									.jsonArrayLocation("������Ϣ��", "dorname")].getBytes(), -1, -1);
							Util.fileAppend("temp1", "\t".getBytes(), -1, -1);
//							Util.fileAppend("temp1", new String(Ram.a,num2,i-1-num2).split("\t")[Util.jsonArrayLocation("ѧ����", "ssex")].getBytes(),-1,-1);
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
