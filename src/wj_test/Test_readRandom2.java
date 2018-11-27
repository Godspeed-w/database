package wj_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import bean.ColumnData;
import util.Util;

public class Test_readRandom2 {
	public static void main(String[] args) throws IOException {
		
		//读配置文件，获得列的个数
		JSONTokener jt = new JSONTokener(new FileReader("db/school/学生表.json"));
		JSONObject jo = (JSONObject)jt.nextValue();
		int numColumn = jo.getJSONArray("column").length();
		System.out.println(jo.getJSONArray("column").getString(2));
		File file=new File("db/school/学生表.txt");
		FileInputStream in= new FileInputStream(file);
		ArrayList<ColumnData> list = new ArrayList<ColumnData>();
		int n=0,i=0,begin=0,row=1,column=0;
		while((n=in.read())>0) {
			i++;
			if(n==9){
				ColumnData data=new ColumnData();
				data.setCloumn(jo.getJSONArray("column").getString(column%numColumn));
				data.setData(Util.readRandom(file, begin, i-1));	
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
				data.setData(Util.readRandom(file, begin, i-2));	
				data.setBegin(begin);
				data.setEnd(i-2);
				data.setRow(row);
				list.add(data); 
				begin = i;
				row++;
				column++;
			}
		}
		
		int temp=0;
		for (ColumnData columnData : list) {
			if(temp%numColumn==0&&temp!=0){
				System.out.println();
			}
			temp++;
			System.out.print(columnData.getData()+" 行号: "+columnData.getRow()+" 列名："+columnData.getCloumn()+" ");
		}
		
//		System.out.print("\n"+Util.readRandom(file, 0, 4));
//		System.out.print(Util.readRandom(file, 5, 11));
//		System.out.print(Util.readRandom(file, 12, 16));
//		System.out.println(Util.readRandom(file, 38, 43));
//		T017	崔嘉鼎	讲师	4111	18045386734	04
//		T005	赵洪	讲师	4300	13203122701	01
	}
	
}
