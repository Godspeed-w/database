package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.json.JSONTokener;

import util.Util;

public class T_second_1 {
	/**
	 * 1���ṩ���������볷���Ľ��档�����������û���������һ�������ֶκ�
	 * ���򽫼����Щ�ֶ��Ƿ��ʺ�����������������ʾ������ʺϣ����¼��������
	 * ������������������Լ�������ֺ󣬽�������������
	 * @param args
	 * @throws IOException 
	 */
public static void main(String[] args) throws IOException {
	String tableName = "ѧ����";
	String conditions ="sname,dorno,depno";//����ѯ���ֶ�
	String flag ="1";//��������ӻ��ǳ�������,1��ʾ��� 0��ʾɾ��
	JSONTokener jt=null;
	JSONObject jo=null;
	if(flag.equals("1")) {
		String judge="";
		for (int i = 0; i < conditions.split(",").length; i++) {
			boolean isCould=true;
			ArrayList<String> list = Util.selectByFlag(conditions.split(",")[i], tableName, "all");
			System.out.println(list);
			Collections.sort(list, new Comparator< String>() {
				@Override
				public int compare(String o1, String o2) {
					if(o1.compareTo(o2)>0) {
						return 	1;
					}else {
						return -1;
					}
				}
			});
			//�ж��Ƿ���ȱʧ�ظ�
			for (int j = 0; j < list.size()-1; j++) {
				if(list.get(j).equals(list.get(j+1))) {
					System.out.println("�ظ���Ϣ��"+list.get(j));
					isCould=false;
					break;
				}
			}
			if(isCould) {
				//�ж��Ƿ�Ϊ��null
				for (String string : list) {
					if(string.equals("null")) {
						System.out.println("���ֿ�����");
						isCould=false;
						break;
					}
				}
			}
			if(isCould) {
				judge +="1";
			}else {
				judge+="0";
			}
		}
		//�ж���ϵ����
		if(conditions.split(",").length!=1) {
			boolean isCould=true;
			ArrayList<String> list = Util.selectByFlag(conditions, tableName, "all");
//			System.out.println(list);
			ArrayList<String> list1= new ArrayList<String>();
			String temp="";
			for (int i = 0; i < list.size(); i++) {
				if(i==0) {
					temp +=list.get(i);
				}else {
					if(i%conditions.split(",").length!=0) {
						temp +=list.get(i);
					}else {
						list1.add(temp);
						temp="";
						temp +=list.get(i);
					}
				}
			}
//			System.out.println(list1);
			Collections.sort(list1, new Comparator< String>() {
				@Override
				public int compare(String o1, String o2) {
					if(o1.compareTo(o2)>0) {
						return 	1;
					}else {
						return -1;
					}
				}
			});
			//�ж��Ƿ����ظ�
			for (int j = 0; j < list1.size()-1; j++) {
				if(list1.get(j).equals(list1.get(j+1))) {
					System.out.println("���������ظ���Ϣ��"+list1.get(j));
					isCould=false;
					break;
				}
			}
			if(isCould) {
				//�ж��Ƿ�Ϊ��null
				for (String string : list) {
					if(string.equals("nullnull")) {
						System.out.println("�������������ݣ�");
						isCould=false;
						break;
					}
				}
			}
			if(isCould) {
				judge +="1";
			}else {
				judge+="0";
			}
		}
		System.out.println(judge);
		if(judge.contains("1")) {
			jt= new JSONTokener(new FileReader("db/"+tableName+".json"));
			jo= (JSONObject)jt.nextValue();
			System.out.println(jo.toString());
			System.out.println(jo.getString("tableName"));
			jo.remove("primaryKey");
			jo.put("primaryKey", conditions);
			FileOutputStream fos = new FileOutputStream("db/"+tableName+".json");
			fos.write(jo.toString().getBytes());
			fos.close();
			if(!jo.get("primaryKey").equals("")) {
				System.out.println(conditions+"�ܹ�������"+" ��������ɹ�");
			}else {
				System.out.println(conditions+"�ܹ�������"+" ��������ʧ��");
			}
		}else {
			System.out.println(conditions+"����������");
		}
	}
	if(flag.equals("0")) {
		jt= new JSONTokener(new FileReader("db/"+tableName+".json"));
		jo= (JSONObject)jt.nextValue();
		System.out.println(jo.toString());
		System.out.println(jo.getString("tableName"));
		jo.remove("primaryKey");
		jo.put("primaryKey", "");
		FileOutputStream fos = new FileOutputStream("db/"+tableName+".json");
		fos.write(jo.toString().getBytes());
		fos.close();
		if(jo.get("primaryKey").equals("")) {
			System.out.println("ɾ����������ɹ�");
		}else {
			System.out.println("ɾ����������ʧ��");
		}
	}
	
}
}
