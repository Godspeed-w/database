package wj_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.json.JSONObject;

import util.Util;

public class Test_1 {
	public static void main(String[] args) throws IOException {
		
			//����json�ļ�,��ȡ����г���
//			FileReader fr = new FileReader("db/school/��ʦ��.json");
//			BufferedReader br = new BufferedReader(fr);
//			String str = br.readLine();
//			String str1 = "";
//			while(str!=null) {
//				str1 += str;
//				str = br.readLine();
//			}
//			JSONObject jo = new JSONObject(str1);
//			int numColumnName = jo.getJSONArray("column").length();
			
//			FileInputStream in = new FileInputStream("db/school/��ʦ��.txt");
//			byte [] readByte = new byte[1];
//			int n=0,i=0;
//			while(true) {
//				n=in.read();
//				if(n==10) {
//					i++;
//					System.out.println(i+"��"+n);
//				}
//				System.out.println(n);
//				if(n==-1)break;
//			}
			
			 
			
			//
			
//			while((a=in.read(readByte, 0, readByte.length))!=-1) {				
////				
////				String tt = new String(readByte,"GBK");
//				System.out.println(readByte.);
//			}
//			in.close();
		
//		FileReader fr = new FileReader("db/school/ѧ����.json");
//		BufferedReader br = new BufferedReader(fr);
//		String str = br.readLine();
//		String str1 = "";
//		while(str!=null) {
//			str1 += str;
//			str = br.readLine();
//		}
//		
//		JSONObject jo = new JSONObject(str1);
//		System.out.println("����json\n"+jo.toString());
//		int a=jo.getJSONArray("column").length();
//		System.out.println("����,"+a);
		
		File file=new File("db/school/��ʦ��.txt");
		System.out.println(Util.readRandom(file, 5, 11));
		
//		T017	�޼ζ�	��ʦ	4111	18045386734	04
//		T005	�Ժ�	��ʦ	4300	13203122701	01
	}
	
}
