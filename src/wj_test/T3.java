package wj_test;

import java.io.IOException;

import dao.Method;

public class T3 {
	public static void main(String[] args) throws IOException {
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200905055"));
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200901004"));
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200901026"));
//		System.out.println(Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200902013"));
		String a=Method.selectInfoByTno("��ʦ�ڿα�", "school","T001");
		System.out.println(a);
	}
}
