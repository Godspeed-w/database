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
	 * 2）提供定义外键与撤消的界面。
	 * 定义外键：用户在输入参照表名，字段，被参照表名，字段后，程序记录外键关系，并提供一个外键相关的删除例子。
	 * 撤消外键：输入外键约束的名字后，将撤消此外键关系。
	 * @param args
	 * @throws IOException 
	 */
public static void main(String[] args) throws IOException {
	String tableName = "学生表";//参照表
	String conditions ="sno,sname,ssex,depno,stel,dorno,";//参照表字段
	String tableNameRefer = "系表";//被参照表
	String conditionsRefer ="depno,depname,depttel,depmaster";//被参照表字段
	String flag ="1";//定义是添加还是撤销外键,1表示添加 0表示删除
	JSONTokener jt=null;
	JSONObject jo=null;
	if(flag.equals("1")) {
		//查找相同字段
		String same="";
		for (int i = 0; i < conditions.split(",").length; i++) {
			for (int j = 0; j < conditionsRefer.split(",").length; j++) {
				if(conditions.split(",")[i].equals(conditionsRefer.split(",")[j])) {
					same+=conditions.split(",")[i]+",";
				}
			}
		}
		System.out.println(same);
		//去掉最后一个逗号
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
				//判断是否有重复
				for (int j = 0; j < list.size()-1; j++) {
					if(list.get(j).equals(list.get(j+1))) {
						isCould=false;
						break;
					}
				}
				if(isCould) {
					//判断是否为空null
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
					System.out.println(same+"能够做外键"+" 外键定义成功");
				}else {
					System.out.println(same+"能够做外键"+" 外键定义失败");
				}
			}else {
				System.out.println(conditions+"不能做主键");
			}
		}else {
			System.out.println("不存在外键约束");
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
			System.out.println("删除外键定义成功");
		}else {
			System.out.println("删除外键定义失败");
		}
	}
	
}
}
