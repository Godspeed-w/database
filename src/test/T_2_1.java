package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class T_2_1 {
	/**
	 * 2、给定某个学生的学号，查询选修了这位学生全部课程的学生信息。
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String sno = "200906093";
		ArrayList<String> listCnosFromStu = Util.selectByFlag("cno", "学生选课表", "sno=" + sno);
		System.out.println(listCnosFromStu);
		ArrayList<String> listSnos = Util.selectByFlag("sno", "学生表", "all");
		System.out.println(listSnos);
		String allStu="";
		for (String string : listSnos) {
			if(!string.equals(sno)) {
				ArrayList<String> listCnos = Util.selectByFlag("cno", "学生选课表", "sno="+string);
				boolean flag=true;
				for (String string2 : listCnosFromStu) {
					if(!listCnos.contains(string2)||listCnos.size()==0) {
						flag=false;
					}
				}
				if(flag) {
					System.out.println(listCnos);
					if(listCnosFromStu.size()<=listCnos.size()) {
						allStu +=string +",";
					}
				}
			}
		}
		System.out.println(allStu);
		String response="学号\t姓名\n";
		if(allStu!="") {
			for (int i = 0; i < allStu.split(",").length; i++) {
				ArrayList<String> listResponse = Util.selectByFlag("sno,sname", "学生表", "sno=" + allStu.split(",")[i]);
				response +=listResponse.get(0)+"\t"+listResponse.get(1)+"\n";
			}
		}else {
			System.out.println("没有同学选了这位同学的全部课程");
		}
		System.out.println(response);
	}
}
