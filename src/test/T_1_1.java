package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class T_1_1 {
	/**
	 * 1������ĳ��ѧ����ѧ�ţ���ѯ���ѧ���������Ϣ���������Ա�����ϵ���������������ƣ�
	 * @param args
	 * @throws IOException
	 */
public static void main(String[] args) throws IOException {
	String sno ="200904039";
	ArrayList<String> list=null;
	String response="����\t�Ա�\t����ϵ��\t����������\n";
	list=Util.selectByFlag("sname,ssex,depno,dorno", "ѧ����", "sno="+sno);
	response += list.get(0)+"\t"+list.get(1)+"\t";
	String temp="";
	if(list.get(2).equals("null")) {
		response +="null"+"\t";
	}else {
		temp=Util.selectByFlag("depname", "ϵ��", "depno="+list.get(2)).get(0);		
		response +=temp+"\t";
	}
	if(list.get(3).equals("null")) {
		response +="null"+"\n";
	}else {
		temp=Util.selectByFlag("dorname", "���ұ�", "dorno="+list.get(3)).get(0);		
		response +=temp+"\t";
	}
	list=null;
	temp=null;
	System.gc();
	System.out.println(response);
}
}
