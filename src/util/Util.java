package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import bean.ColumnData;

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
	
	public static ArrayList<ColumnData> fileToList(File fileTxt,File fileJson) throws IOException{
		//读配置文件，获得列的个数
		JSONTokener jt = new JSONTokener(new FileReader(fileJson));
		JSONObject jo = (JSONObject)jt.nextValue();
		int numColumn = jo.getJSONArray("column").length();
		
		FileInputStream in= new FileInputStream(fileTxt);
		ArrayList<ColumnData> list = new ArrayList<ColumnData>();
		int n=0,i=0,begin=0,row=1,column=0;
		while((n=in.read())>0) {
			i++;
			if(n==9){
				ColumnData data=new ColumnData();
				data.setCloumn(jo.getJSONArray("column").getString(column%numColumn));
				data.setData(Util.readRandom(fileTxt, begin, i-1));	
				data.setBegin(begin);
				data.setEnd(i);
				data.setRow(row);
				list.add(data);
				begin=i;
				column++;
			}
			if(n==10) {
				ColumnData data=new ColumnData();
				data.setCloumn(jo.getJSONArray("column").getString(column%numColumn));
				data.setData(Util.readRandom(fileTxt, begin, i-2));	
				data.setBegin(begin);
				data.setEnd(i-2);
				data.setRow(row);
				list.add(data); 
				begin = i;
				row++;
				column++;
			}
		}
		
		//输出
//		int temp=0;
//		for (ColumnData columnData : list) {
//			if(temp%numColumn==0&&temp!=0){
//				System.out.println();
//			}
//			temp++;
//			System.out.print(columnData.getData());
//		}
		return list;
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
