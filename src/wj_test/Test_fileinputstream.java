package wj_test;

import java.io.FileInputStream;
import java.io.IOException;

public class Test_fileinputstream {
	/**
	 * ����   1���� = 2�ֽ�     �ո�/t = 1�ֽ�   Ӣ���ַ�,�����ַ� = 1�ֽ�
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		FileInputStream fis = new FileInputStream("db/aa/copy.txt");
		//���	����	ְ��	����	�ֻ�����	����ϵ���
		//T017	�޼ζ�	��ʦ	4111	18045386734	04
		//���ֽڣ� ���ص����ֽڵ�ʮ���Ʊ�ʾ��
		int a = fis.read();
		System.out.println(a);
//		int b = fis.read();
//		int aa = fis.read();
//		int bb1 = fis.read();
//		int bb2 = fis.read();
//		System.out.println(Integer.toHexString(a)+" "+a+" "+Integer.toHexString(b)+" "+b);

		byte[] by = new byte[6];
		//���������ֽڴ洢��byte�����У����ص������鳤��
		int d = fis.read(by);	
		System.out.println(d);
		
		for(byte bb : by) {
			System.out.print(bb);			
		}
		System.out.println();
		String c = new String(by, "GBK");
		System.out.println(c);
		fis.close();
	}
}
