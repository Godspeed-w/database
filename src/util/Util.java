package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.io.RandomAccessFile;

public class Util {
	/**
	 * �����ȡ�ļ�
	 * @param file
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 */
	public static String readRandom(File file,int start,int end) throws IOException {
		RandomAccessFile raf=new RandomAccessFile(file, "r");
        raf.seek(start);//�ƶ��ļ�ָ��λ��  
        byte[]  buff=new byte[end-start];  
        //���ڱ���ʵ�ʶ�ȡ���ֽ���  
        int length=end-start;  
        //ѭ����ȡ  
        String result ="";
//        System.out.println("���ȣ�"+length+"���鳤�ȣ�"+buff.length);
        raf.read(buff);
        result +=new String(buff);
//        System.out.println("�����������ǣ�"+new String(buff));
        raf.close();
		return result;
	}
	
	/**
	 * ��ʾ��������
	 * @param showDetail
	 * @param strArray
	 */
	public static void showArray(String showDetail,String[] strArray) {
		System.out.println(showDetail);
		for(String str : strArray) {
			System.out.print(str);
		}
		System.out.println();
	}
	/**
	 * ȥ���ɶԵĵ�����������
	 * @param sentence
	 * @return
	 */
	public static String ridQuotes(String sentence) {
		if(sentence.startsWith("(") && sentence.endsWith(")")) {
			sentence = sentence.substring(1,sentence.length()-1).trim(); 
		}
		if(sentence.startsWith("'") && sentence.endsWith("'")) {
			sentence = sentence.substring(1,sentence.length()-1).trim();
		}
		return sentence;
	}
	/**
	 * д���ļ�����
	 * @param sentence
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String writeFile(String sentence , File file) throws IOException {
		if(file.exists()) {
			FileWriter fw = new FileWriter(file);	
			fw.write(sentence);
		}else {
			return "this table is not exist";
		}
		return "OK";
	}
	//׷���ļ�����
	
}
