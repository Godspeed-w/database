package test;

import java.io.IOException;

import util.Util;

public class T_8 {
	/**
	 * 8、朱红恒同学新选了课程：计算机控制理论及应用，成绩为80分，请插入记录。
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String tno = Util.selectByFlag("sno", "学生表", "sname=朱红恒").get(0);
		System.out.println(tno);
		String cno = Util.selectByFlag("cno", "课程信息表", "cname=计算机控制理论及应用").get(0);
		System.out.println(cno);
		String insert = tno + "\t" + cno + "\t80\n";
		Util.fileAppend("学生选课表", insert.getBytes(), -1, -1);
		System.out.println("插入完成");
	}
}
