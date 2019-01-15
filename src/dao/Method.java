package dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONObject;
import org.json.JSONTokener;

import bean.Ram;
import util.Util;

public class Method {
	/**
	 * 1、给定某个学生的学号，查询这个学生的相关信息（姓名，性别，所在系名，所在寝室名称）
	 * 
	 * @param sno
	 * @return
	 * @throws IOException
	 */
	public static String selectNo1(String sno) throws IOException {
		ArrayList<String> list = null;
		String response = "姓名\t性别\t所在系名\t所在寝室名\n";
		list = Util.selectByFlag("sname,ssex,depno,dorno", "学生表", "sno=" + sno);
		response += list.get(0) + "\t" + list.get(1) + "\t";
		String temp = "";
		if (list.get(2).equals("null")) {
			response += "null" + "\t";
		} else {
			temp = Util.selectByFlag("depname", "系表", "depno=" + list.get(2)).get(0);
			response += temp + "\t";
		}
		if (list.get(3).equals("null")) {
			response += "null" + "\n";
		} else {
			temp = Util.selectByFlag("dorname", "寝室信息表", "dorno=" + list.get(3)).get(0);
			response += temp + "\t";
		}
		list = null;
		temp = null;
		System.gc();
		return response;
	}

	public static String selectNo2(String sno) throws IOException {
		ArrayList<String> listCnosFromStu = Util.selectByFlag("cno", "学生选课表", "sno=" + sno);
		System.out.println(listCnosFromStu);
		ArrayList<String> listSnos = Util.selectByFlag("sno", "学生表", "all");
		System.out.println(listSnos);
		String allStu = "";
		for (String string : listSnos) {
			if (!string.equals(sno)) {
				ArrayList<String> listCnos = Util.selectByFlag("cno", "学生选课表", "sno=" + string);
				boolean flag = true;
				for (String string2 : listCnosFromStu) {
					if (!listCnos.contains(string2) || listCnos.size() == 0) {
						flag = false;
					}
				}
				if (flag) {
					System.out.println(listCnos);
					if (listCnosFromStu.size() <= listCnos.size()) {
						allStu += string + ",";
					}
				}
			}
		}
		System.out.println(allStu);
		String response = "学号\t姓名\n";
		if (allStu != "") {
			for (int i = 0; i < allStu.split(",").length; i++) {
				ArrayList<String> listResponse = Util.selectByFlag("sno,sname", "学生表", "sno=" + allStu.split(",")[i]);
				response += listResponse.get(0) + "\t" + listResponse.get(1) + "\n";
			}
		} else {
			System.out.println("没有同学选了这位同学的全部课程");
		}
		System.out.println(response);
		return response;
	}

	public static String selectNo3(String tno) throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "教师授课表", "tno=" + tno);
		String response = "课程号\t平均成绩\n";
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "学生选课表", "cno=" + string);
			int sum = 0;
			for (String string2 : listGrade) {
				sum += Integer.parseInt(string2);
			}
			response += string + "\t"
					+ String.valueOf(new DecimalFormat("0.000").format((double) sum / listGrade.size())) + "\n";
		}
		listCnos = null;
		System.gc();
		if (!response.equals("课程号\t平均成绩\n")) {
			return response;
		}
		return "no result";
	}

	public static String selectNo4() throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "课程信息表", "all");
		System.out.println(listCnos.toString());
		String response = "课程号\t平均成绩\t最高成绩\t不及格人数\n";			
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "学生选课表", "cno=" + string);
			System.out.println(listGrade.toString());
			int maxGrade = 0, unPass = 0, sum = 0;
			for (String string2 : listGrade) {
				sum += Integer.parseInt(string2);
				if (Integer.parseInt(string2) < 60) {
					unPass++;
				}
				if (Integer.parseInt(string2) > maxGrade) {
					maxGrade = Integer.parseInt(string2);
				}
			}
			response += string + "\t"
					+ String.valueOf(new DecimalFormat("0.000").format((double) sum / listGrade.size())) + "\t"
					+ maxGrade + "\t" + unPass + "\n";
			listGrade = null;
		}
		listCnos = null;
		System.gc();
		System.out.println(response);
		return response;
	}

	public static String selectNo5() throws IOException {
		ArrayList<String> listOrder=new ArrayList<String>();
		ArrayList<String> listDors = Util.selectByFlag("dorno", "寝室信息表", "all");
//		System.out.println(listDors.toString());
		String response = "宿舍号\t宿舍平均成绩\n";
		for (int i = 0; i < listDors.size(); i++) {
//			System.out.println(listDors.get(i));
			ArrayList<String> listStu = Util.selectByFlag("sno", "学生表", "dorno=" + listDors.get(i));
			System.out.println(listStu.toString());
			double sum = 0, numCourse = 0, averageGrade = 0;
			for (String string : listStu) {
				ArrayList<String> listGrade = Util.selectByFlag("grade", "学生选课表", "sno=" + string);
//				System.out.print("学号："+string+"成绩"+listGrade.toString());
				for (String string2 : listGrade) {
					sum += Integer.parseInt(string2);
					numCourse++;
				}
				listGrade = null;
//				System.out.println(" 当前课程总数："+numCourse+" 课程总分："+sum);
			}
//			System.out.println("总成绩"+sum+" 课程数 "+numCourse);
			averageGrade = numCourse == 0 ? 0 : sum / numCourse;
			listOrder.add(String.valueOf(new DecimalFormat("0.000").format(averageGrade))+"#"+listDors.get(i));
//			response += listDors.get(i) + "\t" + String.valueOf(new DecimalFormat("0.000").format(averageGrade)) + "\n";
			listStu = null;
		}
		Collections.sort(listOrder, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (o1.compareTo(o2) < 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		for (String string : listOrder) {
			response += string.split("#")[1]+"\t"+string.split("#")[0]+"\n";
		}
		listDors = null;
		System.gc();
		System.out.println(response);
		return response;
	}

	public static String selectNo6() throws IOException {
		ArrayList<String> listStus = Util.selectByFlag("sno", "学生表", "all");
		String response = "学生号\t超过平均成绩的课程号\n";
		for (String string : listStus) {
			ArrayList<String> listCnosFromStu = Util.selectByFlag("cno,grade", "学生选课表", "sno="+string);
			if(listCnosFromStu.size()==0||listCnosFromStu.size()==2) {
				continue;
			}
			System.out.println(listCnosFromStu.toString());
			double sum=0;
			for (int i = 1; i < listCnosFromStu.size(); i=i+2) {	
				sum +=Integer.parseInt(listCnosFromStu.get(i));
			}
			double avg=sum/(listCnosFromStu.size()/2);
			response +=string;
			for (int i = 1; i < listCnosFromStu.size(); i=i+2) {
				int cha=Integer.parseInt(listCnosFromStu.get(i));
				if(cha>avg) {
					response +="\t"+listCnosFromStu.get(i-1);
				}
			}
			response +="\n";
		}
		listStus=null;
		System.gc();
		System.out.println(response);
		return response;
	}

	public static String selectNo7() throws IOException {
		Util.init();
		ArrayList<String> listSno = Util.selectByFlag("sno", "学生表", "all");
		System.out.println(listSno);
		String names = "";
		for (String string : listSno) {
			ArrayList<String> listCno = Util.selectByFlag("cno", "学生选课表", "sno=" + string);
//		System.out.println(string+" ## "+listCno);
			for (String string2 : listCno) {
				ArrayList<String> listFcno = Util.selectByFlag("fcno", "课程信息表", "cno=" + string2);
//			System.out.println(listFcno);
				if (listFcno.get(0) != "null") {
					if (listCno.contains(listFcno.get(0))) {
						System.out.println(string + "#" + listCno + "#" + listFcno);
						names += string + "#";
						break;
					}
				}
				listFcno = null;
			}
			listCno = null;
		}
		System.out.println(listSno.size() + "啊啊啊" + names.split("#").length);
		System.out.println(names);
		String response = "选课不符合规定的学生\n";
		for (int i = 0; i < names.split("#").length; i++) {
			response += Util.selectByFlag("sname", "学生表", "sno=" + names.split("#")[i]).get(0) + "\n";
		}
		listSno = null;
		System.gc();
		System.out.println(response);

		return response;
	}

	public static String selectNo8() throws IOException {
		String tno = Util.selectByFlag("sno", "学生表", "sname=朱红恒").get(0);
		System.out.println(tno);
		String cno = Util.selectByFlag("cno", "课程信息表", "cname=计算机控制理论及应用").get(0);
		System.out.println(cno);
		String insert = tno + "\t" + cno + "\t80\n";
		Util.fileAppend("学生选课表", insert.getBytes(), -1, -1);
		System.out.println("插入完成");
		return "插入完成";
	}

	public static String selectNo9(String num) throws IOException {
//	String num="3";
		switch (num) {
		case "1":
			System.out.println("Not allow");
			return "Not allow";
		case "2":
			ArrayList<String> line = Util.selectByFlag("depno,depname,deptel,depmaster", "系表", "depname=保密专业系");
			String insert = "\0\0\t";
			for (int i = 1; i < line.size(); i++) {
				insert += line.get(i) + "\t";
			}
			System.out.println(insert);
			File file = new File("db/系表.txt");
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			String temp = null;
			long start = 0;
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK");
				long end = raf.getFilePointer();
				if (temp.contains("保密专业系")) {
					System.out.println(temp + "#" + start + "#" + end);
					raf.seek(start);
					raf.write(insert.getBytes());
				} else {
					System.out.println("not " + temp + "#" + start + "#" + end);
				}
				start = end;
			}
			return "置空修改完成";
		case "3":
			String orignDepno = Util.selectByFlag("depno", "系表", "depname=保密专业系").get(0);
			String newDepno = "08";
			NO3(orignDepno, newDepno);
			return "级联修改完成";
		}
		return "no result";
	}

	public static void NO3(String orignDepno, String newDepno) throws IOException {
		// 第一次修改系表
		Util.init();
		boolean flag = true;
		int i = 0, j = 0, num1 = 0, num2 = 0, location = 0;
		String line = null;
		RandomAccessFile raf = new RandomAccessFile("db/系表.txt", "rw");
		while (flag) {
			if (raf.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						line = new String(Ram.a, num2, i - 1 - num2);
						location = Util.jsonArrayLocation("系表", "depno");
						System.out.println(line + "#" + (num1 + num2) + "#" + (num1 + j - 1));
						if (line.split("\t")[location].equals(orignDepno)) {
							int start = num1 + num2, end = 0;
							for (int k = 0; k < line.split("\t").length; k++) {
								if (location == k) {
									start = (k != 0) ? (start + location * ("\t".getBytes().length)) : (start);
									end = start + line.split("\t")[k].length();
									System.out.println("location:" + location + "start:" + start + " end:" + end);
									raf.seek(start);
									raf.write(newDepno.getBytes());
									System.out.println(raf.getFilePointer());
									break;
								} else {
									start += line.split("\t")[k].length();
									System.out.println("k:" + k + "start:" + start);
								}
							}
						}
						num2 = i + 1;
					}
				}
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				Util.init();
			}
		}
		raf.close();
		// 第二次修改学生表
		Util.init();
		flag = true;
		i = 0;
		j = 0;
		num1 = 0;
		num2 = 0;
		location = 0;
		line = null;
		RandomAccessFile raf1 = new RandomAccessFile("db/学生表.txt", "rw");
		while (flag) {
			if (raf1.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						line = new String(Ram.a, num2, i - 1 - num2);
						location = Util.jsonArrayLocation("学生表", "depno");
						System.out.println(line + "#" + (num1 + num2) + "#" + (num1 + j - 1));
						if (line.split("\t")[location].equals(orignDepno)) {
							int start = num1 + num2, end = 0;
							for (int k = 0; k < line.split("\t").length; k++) {
								if (location == k) {
									start = (k != 0) ? (start + location * ("\t".getBytes().length)) : (start);
									end = start + line.split("\t")[k].length();
									System.out.println("location:" + location + "start:" + start + " end:" + end);
									raf1.seek(start);
									raf1.write(newDepno.getBytes());
									System.out.println(raf1.getFilePointer());
									break;
								} else {
									start += line.split("\t")[k].getBytes().length;
									System.out.println("k:" + k + " cond:" + line.split("\t")[k] + " start:" + start);
								}
							}
						}
						num2 = i + 1;
					}
				}
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf1.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				Util.init();
			}
		}
		raf1.close();
		// 第三次修改教师表
		Util.init();
		flag = true;
		i = 0;
		j = 0;
		num1 = 0;
		num2 = 0;
		location = 0;
		line = null;
		RandomAccessFile raf2 = new RandomAccessFile("db/教师表.txt", "rw");
		while (flag) {
			if (raf2.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						line = new String(Ram.a, num2, i - 1 - num2);
						location = Util.jsonArrayLocation("教师表", "depno");
						System.out.println(line + "#" + (num1 + num2) + "#" + (num1 + j - 1));
						if (line.split("\t")[location].equals(orignDepno)) {
							int start = num1 + num2, end = 0;
							for (int k = 0; k < line.split("\t").length; k++) {
								if (location == k) {
									start = (k != 0) ? (start + location * ("\t".getBytes().length)) : (start);
									end = start + line.split("\t")[k].length();
									System.out.println("location:" + location + "start:" + start + " end:" + end);
									raf2.seek(start);
									raf2.write(newDepno.getBytes());
									System.out.println(raf2.getFilePointer());
									break;
								} else {
									start += line.split("\t")[k].getBytes().length;
									System.out.println("k:" + k + " cond:" + line.split("\t")[k] + " start:" + start);
								}
							}
						}
						num2 = i + 1;
					}
				}
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == 0) {
						flag = false;
						break;
					}
				}
				raf2.seek(num1 + j + 1);
				num1 = num1 + j + 1;
				Util.init();
			}
		}
		raf2.close();
	}

	public static String selectNo10(String num) throws IOException {
		RandomAccessFile raf = null;
		String temp = null;
		File fileOld = null;
		File fileOperate = null;
		File fileNew = null;
		switch (num) {
		case "1":
			System.out.println("Not allow");
			return "NOt allow";
		case "2":
			raf = new RandomAccessFile("db/系表.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				if (!temp.contains("电子商务系")) {
					Util.fileAppend("temp系表", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/系表.txt");
			fileOld.delete();
			fileNew = new File("db/系表.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/temp系表.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			System.out.println("执行");
			return "删除完成";
		case "3":
			String depno = Util.selectByFlag("depno", "系表", "depname=电子商务系").get(0);
			// 操作系表
			raf = new RandomAccessFile("db/系表.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				if (!temp.contains("电子商务系")) {
					Util.fileAppend("temp系表", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/系表.txt");
			fileOld.delete();
			fileNew = new File("db/系表.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/temp系表.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			// 操作学生表
			raf = new RandomAccessFile("db/学生表.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				if (!temp.split("\t")[Util.jsonArrayLocation("学生表", "depno")].equals(depno)) {
					Util.fileAppend("temp学生表", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/学生表.txt");
			fileOld.delete();
			fileNew = new File("db/学生表.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/temp学生表.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			// 操作教师表
			raf = new RandomAccessFile("db/教师表.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				String split = temp.split("\t")[Util.jsonArrayLocation("教师表", "depno")].split("\n")[0];
				if (!split.equals(depno)) {
					System.out.println(split + "#" + depno);
					System.out.println(split.equals(depno));
					Util.fileAppend("temp教师表", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/教师表.txt");
			fileOld.delete();
			fileNew = new File("db/教师表.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/temp教师表.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			return "删除完成";
		}
		return "no result";
	}

	public static String selectNoSecond_1(String tableName, String conditions, String flag) throws IOException {
		JSONTokener jt = null;
		JSONObject jo = null;
		if (flag.equals("add")) {
			String judge = "";
			for (int i = 0; i < conditions.split(",").length; i++) {
				boolean isCould = true;
				ArrayList<String> list = Util.selectByFlag(conditions.split(",")[i], tableName, "all");
				Collections.sort(list, new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (o1.compareTo(o2) > 0) {
							return 1;
						} else {
							return -1;
						}
					}
				});
				// 判断是否有缺失重复
				for (int j = 0; j < list.size() - 1; j++) {
					if (list.get(j).equals(list.get(j + 1))) {
						System.out.println("重复信息：" + list.get(j));
						isCould = false;
						break;
					}
				}
				if (isCould) {
					// 判断是否为空null
					for (String string : list) {
						if (string.equals("null")) {
							System.out.println("出现空数据");
							isCould = false;
							break;
						}
					}
				}
				if (isCould) {
					judge += "1";
				} else {
					judge += "0";
				}
			}
			// 判断组合的情况
			if (conditions.split(",").length != 1) {
				boolean isCould = true;
				ArrayList<String> list = Util.selectByFlag(conditions, tableName, "all");
//			System.out.println(list);
				ArrayList<String> list1 = new ArrayList<String>();
				String temp = "";
				for (int i = 0; i < list.size(); i++) {
					if (i == 0) {
						temp += list.get(i);
					} else {
						if (i % conditions.split(",").length != 0) {
							temp += list.get(i);
						} else {
							list1.add(temp);
							temp = "";
							temp += list.get(i);
						}
					}
				}
//			System.out.println(list1);
				Collections.sort(list1, new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						if (o1.compareTo(o2) > 0) {
							return 1;
						} else {
							return -1;
						}
					}
				});
				// 判断是否有重复
				for (int j = 0; j < list1.size() - 1; j++) {
					if (list1.get(j).equals(list1.get(j + 1))) {
						System.out.println("联合主键重复信息：" + list1.get(j));
						isCould = false;
						break;
					}
				}
				if (isCould) {
					// 判断是否为空null
					for (String string : list) {
						if (string.equals("nullnull")) {
							System.out.println("联合主键空数据：");
							isCould = false;
							break;
						}
					}
				}
				if (isCould) {
					judge += "1";
				} else {
					judge += "0";
				}
			}
			System.out.println(judge);
			if (judge.contains("1")) {
				jt = new JSONTokener(new FileReader("db/" + tableName + ".json"));
				jo = (JSONObject) jt.nextValue();
				System.out.println(jo.toString());
				System.out.println(jo.getString("tableName"));
				jo.remove("primaryKey");
				jo.put("primaryKey", conditions);
				FileOutputStream fos = new FileOutputStream("db/" + tableName + ".json");
				fos.write(jo.toString().getBytes());
				fos.close();
				if (!jo.get("primaryKey").equals("")) {
					System.out.println(conditions + "能够做主键" + " 主键定义成功");
					return conditions + "能够做主键" + " 主键定义成功";
				} else {
					System.out.println(conditions + "能够做主键" + " 主键定义失败");
					return conditions + "能够做主键" + " 主键定义失败";
				}
			} else {
				System.out.println(conditions + "不能做主键");
				return conditions + "不能够做主键";
			}
		}
		if (flag.equals("delete")) {
			jt = new JSONTokener(new FileReader("db/" + tableName + ".json"));
			jo = (JSONObject) jt.nextValue();
			System.out.println(jo.toString());
			System.out.println(jo.getString("tableName"));
			jo.remove("primaryKey");
			jo.put("primaryKey", "");
			FileOutputStream fos = new FileOutputStream("db/" + tableName + ".json");
			fos.write(jo.toString().getBytes());
			fos.close();
			if (jo.get("primaryKey").equals("")) {
				System.out.println("删除主键定义成功");
				return "删除主键定义成功";
			} else {
				System.out.println("删除主键定义失败");
				return "删除主键定义失败";
			}
		}
		return "no result";
	}

	public static String selectNoSecond_2(String tableName, String conditions, String tableNameRefer,
			String conditionsRefer, String flag) throws IOException {
		JSONTokener jt = null;
		JSONObject jo = null;
		if (flag.equals("add")) {
			// 查找相同字段
			String same = "";
			for (int i = 0; i < conditions.split(",").length; i++) {
				for (int j = 0; j < conditionsRefer.split(",").length; j++) {
					if (conditions.split(",")[i].equals(conditionsRefer.split(",")[j])) {
						same += conditions.split(",")[i] + ",";
					}
				}
			}
			System.out.println(same);
			// 去掉最后一个逗号
			if (same.split(",").length == 1) {
				same = same.split(",")[0];
			}
			if (same != "") {
				String judge = "";
				boolean isCould = true;
				for (int i = 0; i < same.split(",").length; i++) {
					ArrayList<String> list = Util.selectByFlag(same.split(",")[i], tableNameRefer, "all");
					Collections.sort(list, new Comparator<String>() {
						@Override
						public int compare(String o1, String o2) {
							if (o1.compareTo(o2) > 0) {
								return 1;
							} else {
								return -1;
							}
						}
					});
					// 判断是否有重复
					for (int j = 0; j < list.size() - 1; j++) {
						if (list.get(j).equals(list.get(j + 1))) {
							isCould = false;
							break;
						}
					}
					if (isCould) {
						// 判断是否为空null
						for (String string : list) {
							if (string.equals("null")) {
								isCould = false;
								break;
							}
						}
					}
					if (isCould) {
						judge += "1";
					} else {
						judge += "0";
					}
				}
				if (!judge.contains("0")) {
					jt = new JSONTokener(new FileReader("db/" + tableName + ".json"));
					jo = (JSONObject) jt.nextValue();
					System.out.println(jo.toString());
					System.out.println(jo.getString("tableName"));
					jo.remove("foreignKey");
					jo.put("foreignKey", same);
					FileOutputStream fos = new FileOutputStream("db/" + tableName + ".json");
					fos.write(jo.toString().getBytes());
					fos.close();
					if (!jo.get("foreignKey").equals("")) {
						System.out.println(same + "能够做外键" + " 外键定义成功");
						return same + "能够做外键" + " 外键定义成功";
					} else {
						System.out.println(same + "能够做外键" + " 外键定义失败");
						return same + "能够做外键" + " 外键定义失败";
					}
				} else {
					System.out.println(conditions + "不能做主键");
					return conditions + "不能做主键";
				}
			} else {
				System.out.println("不存在外键约束");
				return "不存在外键约束";
			}
		}
		if (flag.equals("delete")) {
			jt = new JSONTokener(new FileReader("db/" + tableName + ".json"));
			jo = (JSONObject) jt.nextValue();
			System.out.println(jo.toString());
			System.out.println(jo.getString("tableName"));
			jo.remove("foreignKey");
			jo.put("foreignKey", "");
			FileOutputStream fos = new FileOutputStream("db/" + tableName + ".json");
			fos.write(jo.toString().getBytes());
			fos.close();
			if (jo.get("foreignKey").equals("")) {
				System.out.println("删除外键定义成功");
				return "删除外键定义成功";
			} else {
				System.out.println("删除外键定义失败");
				return "删除外键定义失败";
			}
		}
		return "no result";
	}
//public static String selectNo2(String sno) throws IOException {
//
//return "no result";
//}

}
