package test;

import java.io.IOException;
import java.util.ArrayList;

import util.Util;

public class T_2_1 {
	/**
	 * 2������ĳ��ѧ����ѧ�ţ���ѯѡ������λѧ��ȫ���γ̵�ѧ����Ϣ��
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String sno = "200906093";
		ArrayList<String> listCnosFromStu = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno=" + sno);
		System.out.println(listCnosFromStu);
		ArrayList<String> listSnos = Util.selectByFlag("sno", "ѧ����", "all");
		System.out.println(listSnos);
		String allStu="";
		for (String string : listSnos) {
			if(!string.equals(sno)) {
				ArrayList<String> listCnos = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno="+string);
				boolean flag=true;
				for (String string2 : listCnosFromStu) {
					if(!listCnos.contains(string2)||listCnos.size()==0) {
						flag=false;
					}
				}
				if(flag) {
					System.out.println(listCnos);
					if(listCnosFromStu.size()<=listCnos.size()) {
						allStu +=string +",";
					}
				}
			}
		}
		System.out.println(allStu);
		String response="ѧ��\t����\n";
		if(allStu!="") {
			for (int i = 0; i < allStu.split(",").length; i++) {
				ArrayList<String> listResponse = Util.selectByFlag("sno,sname", "ѧ����", "sno=" + allStu.split(",")[i]);
				response +=listResponse.get(0)+"\t"+listResponse.get(1)+"\n";
			}
		}else {
			System.out.println("û��ͬѧѡ����λͬѧ��ȫ���γ�");
		}
		System.out.println(response);
	}
}
