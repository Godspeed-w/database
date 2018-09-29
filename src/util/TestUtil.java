package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberInputStream;

public class TestUtil {
	//显示数组数据
	public static void showArray(String showDetail,String[] strArray) {
		System.out.println(showDetail);
		for(String str : strArray) {
			System.out.print(str);
		}
		System.out.println();
	}
	//去掉成对的单引号与括号
	public static String ridQuotes(String sentence) {
		if(sentence.startsWith("(") && sentence.endsWith(")")) {
			sentence = sentence.substring(1,sentence.length()-1).trim(); 
		}
		if(sentence.startsWith("'") && sentence.endsWith("'")) {
			sentence = sentence.substring(1,sentence.length()-1).trim();
		}
		return sentence;
	}
	//写新文件内容
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
