package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class T_7 {
	/**
	 * 7��������Щѡ�β����Ϲ涨����ѡ����ͬʱѡ��ĳ�ſμ������пΣ���ѧ��������
	 * @param args
	 */
public static void main(String[] args) throws IOException {
	Util.init();
	ArrayList<String> listSno = Util.selectByFlag("sno", "ѧ����", "all");
	System.out.println(listSno);
	String names="";
	for (String string : listSno) {
		ArrayList<String> listCno = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno="+string);
//		System.out.println(string+" ## "+listCno);
		for (String string2 : listCno) {
			ArrayList<String> listFcno = Util.selectByFlag("fcno", "�γ���Ϣ��", "cno="+string2);
//			System.out.println(listFcno);
			if(listFcno.get(0)!="null") {
				if(listCno.contains(listFcno.get(0))) {
					System.out.println(string+"#"+listCno+"#"+listFcno);
					names += string+"#";
					break;
				}
			}
			listFcno=null;
		}
		listCno=null;
	}
	System.out.println(listSno.size()+"������"+names.split("#").length);
	System.out.println(names);
	String response="ѡ�β����Ϲ涨��ѧ��\n";
	for (int i = 0; i < names.split("#").length; i++) {
		response += Util.selectByFlag("sname", "ѧ����", "sno="+names.split("#")[i]).get(0)+"\n";
	}
	listSno=null;
	System.gc();
	System.out.println(response);
}
}
