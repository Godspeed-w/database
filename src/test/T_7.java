package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class T_7 {
	/**
	 * 7、查找那些选课不符合规定（即选课中同时选了某门课及其先行课）的学生姓名。
	 * @param args
	 */
public static void main(String[] args) throws IOException {
	Util.init();
	ArrayList<String> listSno = Util.selectByFlag("sno", "学生表", "all");
	System.out.println(listSno);
	String names="";
	for (String string : listSno) {
		ArrayList<String> listCno = Util.selectByFlag("cno", "学生选课表", "sno="+string);
//		System.out.println(string+" ## "+listCno);
		for (String string2 : listCno) {
			ArrayList<String> listFcno = Util.selectByFlag("fcno", "课程信息表", "cno="+string2);
//			System.out.println(listFcno);
			if(listFcno.get(0)!="null") {
				if(listCno.contains(listFcno.get(0))) {
					System.out.println(string+"#"+listCno+"#"+listFcno);
					names += string+"#";
					break;
				}
			}
			listFcno=null;
		}
		listCno=null;
	}
	System.out.println(listSno.size()+"啊啊啊"+names.split("#").length);
	System.out.println(names);
	String response="选课不符合规定的学生\n";
	for (int i = 0; i < names.split("#").length; i++) {
		response += Util.selectByFlag("sname", "学生表", "sno="+names.split("#")[i]).get(0)+"\n";
	}
	listSno=null;
	System.gc();
	System.out.println(response);
}
}
