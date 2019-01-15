package test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import util.Util;

public class T_4 {
	/**
	 * 4��ͳ��ÿ�ſγ̵ĳɼ���Ϣ��ƽ���ɼ�����߳ɼ�����ͳɼ�����������������
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "�γ���Ϣ��", "all");
		System.out.println(listCnos.toString());
		String response = "�γ̺�\tƽ���ɼ�\t��߳ɼ�\t����������\n";
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "cno=" + string);
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
