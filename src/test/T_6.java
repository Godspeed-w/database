package test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import util.Util;

public class T_6 {
	/**
	 * 6���ҳ�ÿ��ѧ��������ѡ�޿γ�ƽ���ɼ��Ŀγ̺š�
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Util.init();
		ArrayList<String> listCnosFromStu = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno=200904018");
		System.out.println(listCnosFromStu.toString());
		ArrayList<String> listCnos = Util.selectByFlag("cno", "�γ���Ϣ��", "all");
//		System.out.println(listCnos.toString());

		for (int i = 0; i < listCnos.size(); i++) {
			String temp = listCnos.get(i);
			ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "cno=" + temp);
//			System.out.println(listGrade.toString());
			int sum = 0;
			for (String string2 : listGrade) {
				sum += Integer.parseInt(string2);
			}
			listCnos.set(i, temp + "#" + String.valueOf(sum / listGrade.size()));
			listGrade = null;
		}
		System.out.println(listCnos);
		int averageFromStu = 0;
		for (String string : listCnos) {
			for (String string2 : listCnosFromStu) {
				if (string.substring(0, 4).equals(string2)) {
					averageFromStu += Integer.parseInt(string.substring(5, string.length()));
					System.out.println(averageFromStu + " " + string2);
				}
			}
		}
		averageFromStu /= listCnosFromStu.size();
		System.out.println(averageFromStu);
		String response = "����ƽ���ɼ��Ŀγ̺�\n";
		boolean flag = true;
		for (String string : listCnos) {
			if (!listCnosFromStu.contains(string.substring(0, 4))) {
				if (Integer.parseInt(string.substring(5, string.length())) > averageFromStu) {
					response += string.substring(0, 4) + "\n";
				}
			}
		}
		listCnos = null;
		listCnosFromStu = null;
		System.gc();
		System.out.println(response);
	}
}
