package test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import util.Util;

public class T_6_1 {
	/**
	 * 6���ҳ�ÿ��ѧ��������ѡ�޿γ�ƽ���ɼ��Ŀγ̺š�
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<String> listStus = Util.selectByFlag("sno", "ѧ����", "all");
		String response = "ѧ����\t����ƽ���ɼ��Ŀγ̺�\n";
		for (String string : listStus) {
			ArrayList<String> listCnosFromStu = Util.selectByFlag("cno,grade", "ѧ��ѡ�α�", "sno="+string);
			if(listCnosFromStu.size()==0||listCnosFromStu.size()==2) {
				continue;
			}
			System.out.println(listCnosFromStu.toString());
			double sum=0;
			for (int i = 1; i < listCnosFromStu.size(); i=i+2) {	
				sum +=Integer.parseInt(listCnosFromStu.get(i));
			}
			double avg=sum/(listCnosFromStu.size()/2);
			response +=string;
			for (int i = 1; i < listCnosFromStu.size(); i=i+2) {
				int cha=Integer.parseInt(listCnosFromStu.get(i));
				if(cha>avg) {
					response +="\t"+listCnosFromStu.get(i-1);
				}
			}
			response +="\n";
		}
		listStus=null;
		System.gc();
		System.out.println(response);
	}
}
