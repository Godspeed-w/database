package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Ram;

public class Util {
	/**
	 * 模拟释放内存
	 */
	public static void init() {
		for (int i = 0; i < Ram.N; i++) {
			Ram.a[i] = 0;
		}
	}

	/**
	 * 输出文件信息
	 * 
	 * @param tablename
	 * @throws IOException
	 */
	public static String out(String tablename) throws IOException {
		Util.init();
		String response = "";
		RandomAccessFile raf = new RandomAccessFile("db/" + tablename + ".txt", "r");
		boolean flag = true;
		int j = 0, num1 = 0, num2 = 0;
		while (flag) {
			if (raf.read(Ram.a) != -1) {
				for (int i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
					}
				}
				response += new String(Ram.a, 0, j);
				System.out.print(new String(Ram.a, 0, j));
				for (int i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf.seek(num1 + j);
				num1 = num1 + j;
				Util.init();
			}
		}
		raf.close();
		return response;
	}

	/**
	 * 向文件追加数据
	 * 
	 * @param fileName
	 * @param bt
	 * @param off
	 * @param len
	 * @throws IOException
	 */
	public static void fileAppend(String fileName, byte[] bt, int off, int len) throws IOException {
//		System.out.println("off:"+off+" len:"+len);
//		System.out.println("收到的句子##"+new String(bt));
		File file = new File("db/" + fileName + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(raf.length());
		if (off == -1 && len == -1) {
			raf.write(bt);
		} else {
			raf.write(bt, off, len);
		}
		raf.close();
	}

	/**
	 * 获取数据表序号 ## 从0开始计数
	 * 
	 * @param tableName
	 * @param condition
	 * @return
	 * @throws IOException
	 */
	public static int jsonArrayLocation(String tableName, String condition) throws IOException {
		FileReader fr = new FileReader("db/" + tableName + ".json");
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		String str1 = "";
		while (str != null) {
			str1 += str;
			str = br.readLine();
		}
		br.close();
		JSONObject jo = new JSONObject(str1);
		JSONArray jo2 = jo.getJSONArray("column");
		for (int i = 0; i < jo2.length(); i++) {
			if (jo2.get(i).toString().equals(condition)) {
				return i;
			}
		}
		return -666;
	}

	/**
	 * 获取数据表中字段总数
	 * 
	 * @param tableName
	 * @param condition
	 * @return
	 * @throws IOException
	 */
	public static int jsonArraySize(String tableName) throws IOException {
		FileReader fr = new FileReader("db/" + tableName + ".json");
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		String str1 = "";
		while (str != null) {
			str1 += str;
			str = br.readLine();
		}
		br.close();
		JSONObject jo = new JSONObject(str1);
		JSONArray jo2 = jo.getJSONArray("column");
		return jo2.length();
	}

	/**
	 * 根据条件去查询 select sname,ssex,depno,dorno from 学生表 where sno=200906093;
	 * 
	 * @param args
	 * @throws IOException
	 */
//	public static ArrayList<String> selectByFlag(String flags, String tableName, String condition) throws IOException {
//		Util.init();
//		boolean flag = true;
//		int i = 0, j = 0, num1 = 0, num2 = 0,location=0;
//		String line=null;
//		ArrayList<String> list = new ArrayList<String>();
//		RandomAccessFile raf = new RandomAccessFile("db/" + tableName + ".txt", "r");
//		while (flag) {
//			if (raf.read(Ram.a) != -1) {
//				num2 = 0;//记录400字节中每行开始位置
//				for (i = 0; i < Ram.a.length; i++) {//i迭代循环内存大小
//					if (Ram.a[i] == '\n') {
//						j = i;//j用来记录400字节的最大行末尾指针位置
//						line=new String(Ram.a, num2, i - 1 - num2);
//						if(condition.equals("all")) {//无条件查询
//							for (int k = 0; k < flags.split(",").length; k++) {
//								location =Util.jsonArrayLocation(tableName, flags.split(",")[k]);//定位json中字段位置
//								if(location<line.split("\t").length) {//防止空数据
//									list.add(line.split("\t")[location]);
//								}
//							}
//						}else {//有条件查询
//							location=Util.jsonArrayLocation(tableName,condition.split("=")[0]);
//							if(location<line.split("\t").length) {
//								if (line.split("\t")[location].equals(condition.split("=")[1])) {
//									for (int k = 0; k < flags.split(",").length; k++) {
//										list.add(line.split("\t")[Util.jsonArrayLocation(tableName, flags.split(",")[k])]);
//									}
//								}
//							}
//						}
//						num2 = i + 1;
//					}
//				}
//				for (i = 0; i < Ram.a.length; i++) {
//					if (Ram.a[i] == 0) {
//						flag = false;
//						break;
//					}
//				}
//				raf.seek(num1 + j + 1);
//				num1 = num1 + j + 1;//num1表示当前文件指针位置
//				Util.init();
//			}
//		}
//		raf.close();
//		return list;
//	}

	/**
	 * 弃用上面的方法，使用最新方法
	 */
	public static ArrayList<String> selectByFlag(String flags, String tableName, String condition) throws IOException {
		synchronized (Ram.a) {
			Util.init();
			boolean flag = true;
			int i = 0, j = 0, num1 = 0, num2 = 0, location = 0;
			ArrayList<String> list = new ArrayList<String>();
			RandomAccessFile raf = new RandomAccessFile("db/" + tableName + ".txt", "r");
			location = Util.jsonArrayLocation(tableName, condition.split("=")[0]);
			while (flag) {
				if (raf.read(Ram.a) != -1) {
					num2 = 0;// 记录400字节中每行开始位置
					for (i = 0; i < Ram.a.length; i++) {// i迭代循环内存大小
						if (Ram.a[i] == '\n') {
//						line=new String(Ram.a, num2, i - 1 - num2);
//						System.out.println(line);

//						String[] temp = new String[jsonArraySize(tableName)];
							String temp = "";
							int times = 0;
							int begin = num2;
							String line = "";
							for (int k = num2; k <= i - 1; k++) {
								if (Ram.a[k] == 9 || Ram.a[k] == 10 || k == i - 1) {
									if (k - begin == 0) {
										temp = "null";
									} else {
										temp = new String(Ram.a, begin, k - begin);
									}
//								System.out.println("#"+temp[times]+"#");
									line += temp + "\t";
									begin = k + 1;
									times++;
								}
							}
							if (condition.equals("all")) {
								for (int k = 0; k < flags.split(",").length; k++) {
									list.add(line.split("\t")[Util.jsonArrayLocation(tableName, flags.split(",")[k])]);
								}
							} else {
								location = jsonArrayLocation(tableName, condition.split("=")[0]);
//							System.out.println("location"+location+" "+line.split("\t")[location]+" "+condition.split("=")[1]);
//							System.out.println(line.split("\t")[location].equals(condition.split("=")[1]));
								if (line.split("\t")[location].equals(condition.split("=")[1])) {
									
									for (int k = 0; k < flags.split(",").length; k++) {
										list.add(line.split("\t")[Util.jsonArrayLocation(tableName,
												flags.split(",")[k])]);
									}
								}
							}
							num2 = i + 1;
							j = num2;// j用来记录读取字节的最大行末尾指针位置
						}
					}
					// 判断是否文件读取结束
					for (i = 0; i < Ram.a.length; i++) {
						if (Ram.a[i] == 0) {
							flag = false;
							break;
						}
					}
					raf.seek(num1 + j);
					num1 = num1 + j;// num1表示当前文件指针位置
					Util.init();
				}
			}
			raf.close();
			return list;

		}
	}
}
