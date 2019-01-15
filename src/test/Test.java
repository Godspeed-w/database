package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class Test {
	public static void main(String[] args) throws IOException {
//		System.out.println(Util.jsonArrayLocation("教师表", "tname"));
//		Util.fileAppend("tt", "nj你好jkj".getBytes(), -1, -1);
//		Util.out("课程信息表");
//		System.out.println(Util.jsonArrayLocation("学生表", "sno"));
//		System.out.println(Util.selectByFlag("sno,dorno", "学生表", "all"));
//		System.out.println("123".compareTo("1145"));
//		ArrayList<String> listCno = Util.selectByFlag("cno", "学生选课表", "sno=200904059");
//		for (String string2 : listCno) {
//			ArrayList<String> listFcno = Util.selectByFlag("fcno", "课程信息表", "fcno="+string2);
//			System.out.println(string2+"##"+listFcno);
//			System.out.println(listFcno.size());
//		}
//		ArrayList<String> listFcno = Util.selectByFlag("fcno", "课程信息表", "cno=C002");
//		System.out.println("##"+listFcno);
//		System.out.println("123\t\t456".split("\t").length);
//		System.out.println("123\t456\t\t".split("\t").length);
//		ArrayList<String> list=Util.selectByFlag2("fcno,cname", "课程信息表", "all");
//		for (String string : list) {
//			System.out.println(string);
//		}
		ArrayList<String> list=Util.selectByFlag("tno,tname", "教师表", "all");
		System.out.println(list.toString());
		
		ArrayList<String> list1=Util.selectByFlag("tno,ttel,tname", "教师表", "tno=T005");
		System.out.println(list1.toString());
		
		ArrayList<String> list2=Util.selectByFlag("sno,stel,sname,depno", "学生表", "tno=T005");
		System.out.println(list2.toString());
	}
}
