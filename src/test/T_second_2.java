package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONObject;
import org.json.JSONTokener;

import util.Util;

public class T_second_2 {
	/**
	 * 2���ṩ��������볷���Ľ��档
	 * ����������û���������ձ������ֶΣ������ձ������ֶκ󣬳����¼�����ϵ�����ṩһ�������ص�ɾ�����ӡ�
	 * ����������������Լ�������ֺ󣬽������������ϵ��
	 * @param args
	 * @throws IOException 
	 */
public static void main(String[] args) throws IOException {
	String tableName = "ѧ����";//���ձ�
	String conditions ="sno,sname,ssex,depno,stel,dorno,";//���ձ��ֶ�
	String tableNameRefer = "ϵ��";//�����ձ�
	String conditionsRefer ="depno,depname,depttel,depmaster";//�����ձ��ֶ�
	String flag ="1";//��������ӻ��ǳ������,1��ʾ��� 0��ʾɾ��
	JSONTokener jt=null;
	JSONObject jo=null;
	if(flag.equals("1")) {
		//������ͬ�ֶ�
		String same="";
		for (int i = 0; i < conditions.split(",").length; i++) {
			for (int j = 0; j < conditionsRefer.split(",").length; j++) {
				if(conditions.split(",")[i].equals(conditionsRefer.split(",")[j])) {
					same+=conditions.split(",")[i]+",";
				}
			}
		}
		System.out.println(same);
		//ȥ�����һ������
		if(same.split(",").length==1) {
			same = same.split(",")[0];
		}
		if(same!="") {
			String judge="";
			boolean isCould=true;
			for (int i = 0; i < same.split(",").length; i++) {
				ArrayList<String> list = Util.selectByFlag(same.split(",")[i], tableNameRefer, "all");
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
				//�ж��Ƿ����ظ�
				for (int j = 0; j < list.size()-1; j++) {
					if(list.get(j).equals(list.get(j+1))) {
						isCould=false;
						break;
					}
				}
				if(isCould) {
					//�ж��Ƿ�Ϊ��null
					for (String string : list) {
						if(string.equals("null")) {
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
			if(!judge.contains("0")) {
				jt= new JSONTokener(new FileReader("db/"+tableName+".json"));
				jo= (JSONObject)jt.nextValue();
				System.out.println(jo.toString());
				System.out.println(jo.getString("tableName"));
				jo.remove("foreignKey");
				jo.put("foreignKey", same);
				FileOutputStream fos = new FileOutputStream("db/"+tableName+".json");
				fos.write(jo.toString().getBytes());
				fos.close();
				if(!jo.get("foreignKey").equals("")) {
					System.out.println(same+"�ܹ������"+" �������ɹ�");
				}else {
					System.out.println(same+"�ܹ������"+" �������ʧ��");
				}
			}else {
				System.out.println(conditions+"����������");
			}
		}else {
			System.out.println("���������Լ��");
		}
	}
	if(flag.equals("0")) {
		jt= new JSONTokener(new FileReader("db/"+tableName+".json"));
		jo= (JSONObject)jt.nextValue();
		System.out.println(jo.toString());
		System.out.println(jo.getString("tableName"));
		jo.remove("foreignKey");
		jo.put("foreignKey", "");
		FileOutputStream fos = new FileOutputStream("db/"+tableName+".json");
		fos.write(jo.toString().getBytes());
		fos.close();
		if(jo.get("foreignKey").equals("")) {
			System.out.println("ɾ���������ɹ�");
		}else {
			System.out.println("ɾ���������ʧ��");
		}
	}
	
}
}
