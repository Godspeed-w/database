package test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import util.Util;

public class T_5 {
	/**
	 * 5����ѯÿ��������ס��ͬѧ��ƽ���ɼ���Ϣ������ƽ���ɼ��Ӹߵ������С�
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		ArrayList<String> listOrder=new ArrayList<String>();
		ArrayList<String> listDors = Util.selectByFlag("dorno", "������Ϣ��", "all");
//		System.out.println(listDors.toString());
		String response = "�����\t����ƽ���ɼ�\n";
		for (int i = 0; i < listDors.size(); i++) {
//			System.out.println(listDors.get(i));
			ArrayList<String> listStu = Util.selectByFlag("sno", "ѧ����", "dorno=" + listDors.get(i));
			System.out.println(listStu.toString());
			double sum = 0, numCourse = 0, averageGrade = 0;
			for (String string : listStu) {
				ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "sno=" + string);
//				System.out.print("ѧ�ţ�"+string+"�ɼ�"+listGrade.toString());
				for (String string2 : listGrade) {
					sum += Integer.parseInt(string2);
					numCourse++;
				}
				listGrade = null;
//				System.out.println(" ��ǰ�γ�������"+numCourse+" �γ��ܷ֣�"+sum);
			}
//			System.out.println("�ܳɼ�"+sum+" �γ��� "+numCourse);
			averageGrade = numCourse == 0 ? 0 : sum / numCourse;
			listOrder.add(String.valueOf(new DecimalFormat("0.000").format(averageGrade))+"#"+listDors.get(i));
//			response += listDors.get(i) + "\t" + String.valueOf(new DecimalFormat("0.000").format(averageGrade)) + "\n";
			listStu = null;
		}
		Collections.sort(listOrder, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.compareTo(o2) < 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		for (String string : listOrder) {
			response += string.split("#")[1]+"\t"+string.split("#")[0]+"\n";
		}
		listDors = null;
		System.gc();
		System.out.println(response);
	}
}
