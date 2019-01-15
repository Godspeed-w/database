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
	 * 1）提供定义主键与撤消的界面。定义主键：用户可以输入一个或多个字段后，
	 * 程序将检查这些字段是否适合做主键，并给出提示，如果适合，则记录此主键。
	 * 撤消主键：输入主键约束的名字后，将撤消此主键。
	 * @param args
	 * @throws IOException 
	 */
public static void main(String[] args) throws IOException {
	String tableName = "学生表";
	String conditions ="sname,dorno,depno";//待查询的字段
	String flag ="1";//定义是添加还是撤销主键,1表示添加 0表示删除
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
			//判断是否有缺失重复
			for (int j = 0; j < list.size()-1; j++) {
				if(list.get(j).equals(list.get(j+1))) {
					System.out.println("重复信息："+list.get(j));
					isCould=false;
					break;
				}
			}
			if(isCould) {
				//判断是否为空null
				for (String string : list) {
					if(string.equals("null")) {
						System.out.println("出现空数据");
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
		//判断组合的情况
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
			//判断是否有重复
			for (int j = 0; j < list1.size()-1; j++) {
				if(list1.get(j).equals(list1.get(j+1))) {
					System.out.println("联合主键重复信息："+list1.get(j));
					isCould=false;
					break;
				}
			}
			if(isCould) {
				//判断是否为空null
				for (String string : list) {
					if(string.equals("nullnull")) {
						System.out.println("联合主键空数据：");
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
				System.out.println(conditions+"能够做主键"+" 主键定义成功");
			}else {
				System.out.println(conditions+"能够做主键"+" 主键定义失败");
			}
		}else {
			System.out.println(conditions+"不能做主键");
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
			System.out.println("删除主键定义成功");
		}else {
			System.out.println("删除主键定义失败");
		}
	}
	
}
}
