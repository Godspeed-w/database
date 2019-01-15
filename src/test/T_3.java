package test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.sound.midi.SysexMessage;

import bean.Ram;
import util.Util;

public class T_3 {
	/**
	 * 3、给定某个教师的工号，查询其所上的每门课的平均成绩。
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "教师授课表", "tno=T004");
//		System.out.println(listCnos.toString());
		String response = "课程号\t平均成绩\n";
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "学生选课表", "cno=" + string);
//		System.out.println(string+" "+listGrade.toString());
			int sum = 0;
			for (String string2 : listGrade) {
				sum += Integer.parseInt(string2);
			}
			response += string + "\t"
					+ String.valueOf(new DecimalFormat("0.000").format((double) sum / listGrade.size())) + "\n";
		}
		listCnos = null;
		System.gc();
		System.out.println(response);
	}
}
