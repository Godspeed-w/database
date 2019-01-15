package test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import util.Util;

public class T_4 {
	/**
	 * 4、统计每门课程的成绩信息（平均成绩，最高成绩，最低成绩，不及格人数）。
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "课程信息表", "all");
		System.out.println(listCnos.toString());
		String response = "课程号\t平均成绩\t最高成绩\t不及格人数\n";
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "学生选课表", "cno=" + string);
			System.out.println(listGrade.toString());
			int maxGrade = 0, unPass = 0, sum = 0;
			for (String string2 : listGrade) {
				sum += Integer.parseInt(string2);
				if (Integer.parseInt(string2) < 60) {
					unPass++;
				}
				if (Integer.parseInt(string2) > maxGrade) {
					maxGrade = Integer.parseInt(string2);
				}
			}
			response += string + "\t"
					+ String.valueOf(new DecimalFormat("0.000").format((double) sum / listGrade.size())) + "\t"
					+ maxGrade + "\t" + unPass + "\n";
			listGrade = null;
		}
		listCnos = null;
		System.gc();
		System.out.println(response);
	}
}
