package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class T_1_1 {
	/**
	 * 1、给定某个学生的学号，查询这个学生的相关信息（姓名，性别，所在系名，所在寝室名称）
	 * @param args
	 * @throws IOException
	 */
public static void main(String[] args) throws IOException {
	String sno ="200904039";
	ArrayList<String> list=null;
	String response="姓名\t性别\t所在系名\t所在寝室名\n";
	list=Util.selectByFlag("sname,ssex,depno,dorno", "学生表", "sno="+sno);
	response += list.get(0)+"\t"+list.get(1)+"\t";
	String temp="";
	if(list.get(2).equals("null")) {
		response +="null"+"\t";
	}else {
		temp=Util.selectByFlag("depname", "系表", "depno="+list.get(2)).get(0);		
		response +=temp+"\t";
	}
	if(list.get(3).equals("null")) {
		response +="null"+"\n";
	}else {
		temp=Util.selectByFlag("dorname", "寝室表", "dorno="+list.get(3)).get(0);		
		response +=temp+"\t";
	}
	list=null;
	temp=null;
	System.gc();
	System.out.println(response);
}
}
