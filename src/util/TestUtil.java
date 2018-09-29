package util;

import java.io.LineNumberInputStream;

public class TestUtil {
	//��ʾ��������
	public static void showArray(String showDetail,String[] strArray) {
		System.out.println(showDetail);
		for(String str : strArray) {
			System.out.print(str);
		}
		System.out.println();
	}
	//ȥ���ɶԵĵ�����������
	public static String ridQuotes(String sentence) {
		if(sentence.startsWith("(") && sentence.endsWith(")")) {
			sentence = sentence.substring(1,sentence.length()-1).trim(); 
		}
		if(sentence.startsWith("'") && sentence.endsWith("'")) {
			sentence = sentence.substring(1,sentence.length()-1).trim();
		}
		return sentence;
	}
	
}
