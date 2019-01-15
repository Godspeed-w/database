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
	 * 3������ĳ����ʦ�Ĺ��ţ���ѯ�����ϵ�ÿ�ſε�ƽ���ɼ���
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "��ʦ�ڿα�", "tno=T004");
//		System.out.println(listCnos.toString());
		String response = "�γ̺�\tƽ���ɼ�\n";
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "cno=" + string);
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
