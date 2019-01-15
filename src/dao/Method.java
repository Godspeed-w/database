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
	 * 1������ĳ��ѧ����ѧ�ţ���ѯ���ѧ���������Ϣ���������Ա�����ϵ���������������ƣ�
	 * 
	 * @param sno
	 * @return
	 * @throws IOException
	 */
	public static String selectNo1(String sno) throws IOException {
		ArrayList<String> list = null;
		String response = "����\t�Ա�\t����ϵ��\t����������\n";
		list = Util.selectByFlag("sname,ssex,depno,dorno", "ѧ����", "sno=" + sno);
		response += list.get(0) + "\t" + list.get(1) + "\t";
		String temp = "";
		if (list.get(2).equals("null")) {
			response += "null" + "\t";
		} else {
			temp = Util.selectByFlag("depname", "ϵ��", "depno=" + list.get(2)).get(0);
			response += temp + "\t";
		}
		if (list.get(3).equals("null")) {
			response += "null" + "\n";
		} else {
			temp = Util.selectByFlag("dorname", "������Ϣ��", "dorno=" + list.get(3)).get(0);
			response += temp + "\t";
		}
		list = null;
		temp = null;
		System.gc();
		return response;
	}

	public static String selectNo2(String sno) throws IOException {
		ArrayList<String> listCnosFromStu = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno=" + sno);
		System.out.println(listCnosFromStu);
		ArrayList<String> listSnos = Util.selectByFlag("sno", "ѧ����", "all");
		System.out.println(listSnos);
		String allStu = "";
		for (String string : listSnos) {
			if (!string.equals(sno)) {
				ArrayList<String> listCnos = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno=" + string);
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
		String response = "ѧ��\t����\n";
		if (allStu != "") {
			for (int i = 0; i < allStu.split(",").length; i++) {
				ArrayList<String> listResponse = Util.selectByFlag("sno,sname", "ѧ����", "sno=" + allStu.split(",")[i]);
				response += listResponse.get(0) + "\t" + listResponse.get(1) + "\n";
			}
		} else {
			System.out.println("û��ͬѧѡ����λͬѧ��ȫ���γ�");
		}
		System.out.println(response);
		return response;
	}

	public static String selectNo3(String tno) throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "��ʦ�ڿα�", "tno=" + tno);
		String response = "�γ̺�\tƽ���ɼ�\n";
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "cno=" + string);
			int sum = 0;
			for (String string2 : listGrade) {
				sum += Integer.parseInt(string2);
			}
			response += string + "\t"
					+ String.valueOf(new DecimalFormat("0.000").format((double) sum / listGrade.size())) + "\n";
		}
		listCnos = null;
		System.gc();
		if (!response.equals("�γ̺�\tƽ���ɼ�\n")) {
			return response;
		}
		return "no result";
	}

	public static String selectNo4() throws IOException {
		Util.init();
		ArrayList<String> listCnos = Util.selectByFlag("cno", "�γ���Ϣ��", "all");
		System.out.println(listCnos.toString());
		String response = "�γ̺�\tƽ���ɼ�\t��߳ɼ�\t����������\n";			
		for (String string : listCnos) {
			ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "cno=" + string);
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
		ArrayList<String> listDors = Util.selectByFlag("dorno", "������Ϣ��", "all");
//		System.out.println(listDors.toString());
		String response = "�����\t����ƽ���ɼ�\n";
		for (int i = 0; i < listDors.size(); i++) {
//			System.out.println(listDors.get(i));
			ArrayList<String> listStu = Util.selectByFlag("sno", "ѧ����", "dorno=" + listDors.get(i));
			System.out.println(listStu.toString());
			double sum = 0, numCourse = 0, averageGrade = 0;
			for (String string : listStu) {
				ArrayList<String> listGrade = Util.selectByFlag("grade", "ѧ��ѡ�α�", "sno=" + string);
//				System.out.print("ѧ�ţ�"+string+"�ɼ�"+listGrade.toString());
				for (String string2 : listGrade) {
					sum += Integer.parseInt(string2);
					numCourse++;
				}
				listGrade = null;
//				System.out.println(" ��ǰ�γ�������"+numCourse+" �γ��ܷ֣�"+sum);
			}
//			System.out.println("�ܳɼ�"+sum+" �γ��� "+numCourse);
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
		ArrayList<String> listStus = Util.selectByFlag("sno", "ѧ����", "all");
		String response = "ѧ����\t����ƽ���ɼ��Ŀγ̺�\n";
		for (String string : listStus) {
			ArrayList<String> listCnosFromStu = Util.selectByFlag("cno,grade", "ѧ��ѡ�α�", "sno="+string);
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
		ArrayList<String> listSno = Util.selectByFlag("sno", "ѧ����", "all");
		System.out.println(listSno);
		String names = "";
		for (String string : listSno) {
			ArrayList<String> listCno = Util.selectByFlag("cno", "ѧ��ѡ�α�", "sno=" + string);
//		System.out.println(string+" ## "+listCno);
			for (String string2 : listCno) {
				ArrayList<String> listFcno = Util.selectByFlag("fcno", "�γ���Ϣ��", "cno=" + string2);
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
		System.out.println(listSno.size() + "������" + names.split("#").length);
		System.out.println(names);
		String response = "ѡ�β����Ϲ涨��ѧ��\n";
		for (int i = 0; i < names.split("#").length; i++) {
			response += Util.selectByFlag("sname", "ѧ����", "sno=" + names.split("#")[i]).get(0) + "\n";
		}
		listSno = null;
		System.gc();
		System.out.println(response);

		return response;
	}

	public static String selectNo8() throws IOException {
		String tno = Util.selectByFlag("sno", "ѧ����", "sname=����").get(0);
		System.out.println(tno);
		String cno = Util.selectByFlag("cno", "�γ���Ϣ��", "cname=������������ۼ�Ӧ��").get(0);
		System.out.println(cno);
		String insert = tno + "\t" + cno + "\t80\n";
		Util.fileAppend("ѧ��ѡ�α�", insert.getBytes(), -1, -1);
		System.out.println("�������");
		return "�������";
	}

	public static String selectNo9(String num) throws IOException {
//	String num="3";
		switch (num) {
		case "1":
			System.out.println("Not allow");
			return "Not allow";
		case "2":
			ArrayList<String> line = Util.selectByFlag("depno,depname,deptel,depmaster", "ϵ��", "depname=����רҵϵ");
			String insert = "\0\0\t";
			for (int i = 1; i < line.size(); i++) {
				insert += line.get(i) + "\t";
			}
			System.out.println(insert);
			File file = new File("db/ϵ��.txt");
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			String temp = null;
			long start = 0;
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK");
				long end = raf.getFilePointer();
				if (temp.contains("����רҵϵ")) {
					System.out.println(temp + "#" + start + "#" + end);
					raf.seek(start);
					raf.write(insert.getBytes());
				} else {
					System.out.println("not " + temp + "#" + start + "#" + end);
				}
				start = end;
			}
			return "�ÿ��޸����";
		case "3":
			String orignDepno = Util.selectByFlag("depno", "ϵ��", "depname=����רҵϵ").get(0);
			String newDepno = "08";
			NO3(orignDepno, newDepno);
			return "�����޸����";
		}
		return "no result";
	}

	public static void NO3(String orignDepno, String newDepno) throws IOException {
		// ��һ���޸�ϵ��
		Util.init();
		boolean flag = true;
		int i = 0, j = 0, num1 = 0, num2 = 0, location = 0;
		String line = null;
		RandomAccessFile raf = new RandomAccessFile("db/ϵ��.txt", "rw");
		while (flag) {
			if (raf.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						line = new String(Ram.a, num2, i - 1 - num2);
						location = Util.jsonArrayLocation("ϵ��", "depno");
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
		// �ڶ����޸�ѧ����
		Util.init();
		flag = true;
		i = 0;
		j = 0;
		num1 = 0;
		num2 = 0;
		location = 0;
		line = null;
		RandomAccessFile raf1 = new RandomAccessFile("db/ѧ����.txt", "rw");
		while (flag) {
			if (raf1.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						line = new String(Ram.a, num2, i - 1 - num2);
						location = Util.jsonArrayLocation("ѧ����", "depno");
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
		// �������޸Ľ�ʦ��
		Util.init();
		flag = true;
		i = 0;
		j = 0;
		num1 = 0;
		num2 = 0;
		location = 0;
		line = null;
		RandomAccessFile raf2 = new RandomAccessFile("db/��ʦ��.txt", "rw");
		while (flag) {
			if (raf2.read(Ram.a) != -1) {
				num2 = 0;
				for (i = 0; i < Ram.a.length; i++) {
					if (Ram.a[i] == '\n') {
						j = i;
						line = new String(Ram.a, num2, i - 1 - num2);
						location = Util.jsonArrayLocation("��ʦ��", "depno");
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
			raf = new RandomAccessFile("db/ϵ��.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				if (!temp.contains("��������ϵ")) {
					Util.fileAppend("tempϵ��", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/ϵ��.txt");
			fileOld.delete();
			fileNew = new File("db/ϵ��.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/tempϵ��.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			System.out.println("ִ��");
			return "ɾ�����";
		case "3":
			String depno = Util.selectByFlag("depno", "ϵ��", "depname=��������ϵ").get(0);
			// ����ϵ��
			raf = new RandomAccessFile("db/ϵ��.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				if (!temp.contains("��������ϵ")) {
					Util.fileAppend("tempϵ��", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/ϵ��.txt");
			fileOld.delete();
			fileNew = new File("db/ϵ��.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/tempϵ��.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			// ����ѧ����
			raf = new RandomAccessFile("db/ѧ����.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				if (!temp.split("\t")[Util.jsonArrayLocation("ѧ����", "depno")].equals(depno)) {
					Util.fileAppend("tempѧ����", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/ѧ����.txt");
			fileOld.delete();
			fileNew = new File("db/ѧ����.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/tempѧ����.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			// ������ʦ��
			raf = new RandomAccessFile("db/��ʦ��.txt", "rw");
			while ((temp = raf.readLine()) != null) {
				temp = new String(temp.getBytes("ISO-8859-1"), "GBK") + "\n";
				System.out.println(temp);
				String split = temp.split("\t")[Util.jsonArrayLocation("��ʦ��", "depno")].split("\n")[0];
				if (!split.equals(depno)) {
					System.out.println(split + "#" + depno);
					System.out.println(split.equals(depno));
					Util.fileAppend("temp��ʦ��", temp.getBytes(), -1, -1);
				}
			}
			fileOld = new File("db/��ʦ��.txt");
			fileOld.delete();
			fileNew = new File("db/��ʦ��.txt");
			fileNew.createNewFile();
			fileOperate = new File("db/temp��ʦ��.txt");
			fileOperate.renameTo(fileNew);
			fileOperate.delete();
			return "ɾ�����";
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
				// �ж��Ƿ���ȱʧ�ظ�
				for (int j = 0; j < list.size() - 1; j++) {
					if (list.get(j).equals(list.get(j + 1))) {
						System.out.println("�ظ���Ϣ��" + list.get(j));
						isCould = false;
						break;
					}
				}
				if (isCould) {
					// �ж��Ƿ�Ϊ��null
					for (String string : list) {
						if (string.equals("null")) {
							System.out.println("���ֿ�����");
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
			// �ж���ϵ����
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
				// �ж��Ƿ����ظ�
				for (int j = 0; j < list1.size() - 1; j++) {
					if (list1.get(j).equals(list1.get(j + 1))) {
						System.out.println("���������ظ���Ϣ��" + list1.get(j));
						isCould = false;
						break;
					}
				}
				if (isCould) {
					// �ж��Ƿ�Ϊ��null
					for (String string : list) {
						if (string.equals("nullnull")) {
							System.out.println("�������������ݣ�");
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
					System.out.println(conditions + "�ܹ�������" + " ��������ɹ�");
					return conditions + "�ܹ�������" + " ��������ɹ�";
				} else {
					System.out.println(conditions + "�ܹ�������" + " ��������ʧ��");
					return conditions + "�ܹ�������" + " ��������ʧ��";
				}
			} else {
				System.out.println(conditions + "����������");
				return conditions + "���ܹ�������";
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
				System.out.println("ɾ����������ɹ�");
				return "ɾ����������ɹ�";
			} else {
				System.out.println("ɾ����������ʧ��");
				return "ɾ����������ʧ��";
			}
		}
		return "no result";
	}

	public static String selectNoSecond_2(String tableName, String conditions, String tableNameRefer,
			String conditionsRefer, String flag) throws IOException {
		JSONTokener jt = null;
		JSONObject jo = null;
		if (flag.equals("add")) {
			// ������ͬ�ֶ�
			String same = "";
			for (int i = 0; i < conditions.split(",").length; i++) {
				for (int j = 0; j < conditionsRefer.split(",").length; j++) {
					if (conditions.split(",")[i].equals(conditionsRefer.split(",")[j])) {
						same += conditions.split(",")[i] + ",";
					}
				}
			}
			System.out.println(same);
			// ȥ�����һ������
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
					// �ж��Ƿ����ظ�
					for (int j = 0; j < list.size() - 1; j++) {
						if (list.get(j).equals(list.get(j + 1))) {
							isCould = false;
							break;
						}
					}
					if (isCould) {
						// �ж��Ƿ�Ϊ��null
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
						System.out.println(same + "�ܹ������" + " �������ɹ�");
						return same + "�ܹ������" + " �������ɹ�";
					} else {
						System.out.println(same + "�ܹ������" + " �������ʧ��");
						return same + "�ܹ������" + " �������ʧ��";
					}
				} else {
					System.out.println(conditions + "����������");
					return conditions + "����������";
				}
			} else {
				System.out.println("���������Լ��");
				return "���������Լ��";
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
				System.out.println("ɾ���������ɹ�");
				return "ɾ���������ɹ�";
			} else {
				System.out.println("ɾ���������ʧ��");
				return "ɾ���������ʧ��";
			}
		}
		return "no result";
	}
//public static String selectNo2(String sno) throws IOException {
//
//return "no result";
//}

}
