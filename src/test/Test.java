package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class Test {
	public static void main(String[] args) throws IOException {
//		System.out.println(Util.jsonArrayLocation("��ʦ��", "tname"));
//		Util.fileAppend("tt", "nj���jkj".getBytes(), -1, -1);
//		Util.out("�γ���Ϣ��");
//		System.out.println(Util.jsonArrayLocation("ѧ����", "sno"));
//		System.out.println(Util.selectByFlag("sno,dorno", "ѧ����", "all"));
//		System.out.println("123".compareTo("1145"));
//		ArrayList<String> listCno = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno=200904059");
//		for (String string2 : listCno) {
//			ArrayList<String> listFcno = Util.selectByFlag("fcno", "�γ���Ϣ��", "fcno="+string2);
//			System.out.println(string2+"##"+listFcno);
//			System.out.println(listFcno.size());
//		}
//		ArrayList<String> listFcno = Util.selectByFlag("fcno", "�γ���Ϣ��", "cno=C002");
//		System.out.println("##"+listFcno);
//		System.out.println("123\t\t456".split("\t").length);
//		System.out.println("123\t456\t\t".split("\t").length);
//		ArrayList<String> list=Util.selectByFlag2("fcno,cname", "�γ���Ϣ��", "all");
//		for (String string : list) {
//			System.out.println(string);
//		}
		ArrayList<String> list=Util.selectByFlag("tno,tname", "��ʦ��", "all");
		System.out.println(list.toString());
		
		ArrayList<String> list1=Util.selectByFlag("tno,ttel,tname", "��ʦ��", "tno=T005");
		System.out.println(list1.toString());
		
		ArrayList<String> list2=Util.selectByFlag("sno,stel,sname,depno", "ѧ����", "tno=T005");
		System.out.println(list2.toString());
	}
}
