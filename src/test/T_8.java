package test;

import java.io.IOException;

import util.Util;

public class T_8 {
	/**
	 * 8������ͬѧ��ѡ�˿γ̣�������������ۼ�Ӧ�ã��ɼ�Ϊ80�֣�������¼��
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String tno = Util.selectByFlag("sno", "ѧ����", "sname=����").get(0);
		System.out.println(tno);
		String cno = Util.selectByFlag("cno", "�γ���Ϣ��", "cname=������������ۼ�Ӧ��").get(0);
		System.out.println(cno);
		String insert = tno + "\t" + cno + "\t80\n";
		Util.fileAppend("ѧ��ѡ�α�", insert.getBytes(), -1, -1);
		System.out.println("�������");
	}
}
