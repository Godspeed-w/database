package wj_test;

import java.io.IOException;

import dao.Method;

public class T2 {

	public static void main(String[] args) throws IOException {
//		String b= Method.selectFlagFromTable("��ʦ��", "school", "���,����,ְ��,����", "���=T008");
//		System.out.println(b);
//		String b= Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "�γ̺�=C020");
//		System.out.println(b);
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200907020"));
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200904003"));
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200901016"));
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200905026"));
		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200904003"));
		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200905055"));
		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200901004"));
		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200901026"));
		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200902013"));
		String a=Method.selectInfoByCourse("ѧ��ѡ�α�", "school","200904003");
		System.out.println(a);
	}

}
