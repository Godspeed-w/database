package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.io.RandomAccessFile;

public class Util {
	/**
	 * 随机读取文件
	 * @param file
	 * @param start
	 * @param end
	 * @return
	 * @throws IOException
	 */
	public static String readRandom(File file,int start,int end) throws IOException {
		RandomAccessFile raf=new RandomAccessFile(file, "r");
        raf.seek(start);//移动文件指针位置  
        byte[]  buff=new byte[end-start];  
        //用于保存实际读取的字节数  
        int length=end-start;  
        //循环读取  
        String result ="";
//        System.out.println("长度："+length+"数组长度："+buff.length);
        raf.read(buff);
        result +=new String(buff);
//        System.out.println("读到的数据是："+new String(buff));
        raf.close();
		return result;
	}
	
	/**
	 * 显示数组数据
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
	 * 去掉成对的单引号与括号
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
	 * 写新文件内容
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
	//追加文件内容
	
}
