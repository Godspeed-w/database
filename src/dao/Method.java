package dao;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONTokener;

import bean.ColumnConfig;
import bean.ColumnData;
import bean.ColumnRow;
import bean.TableConfig;
import util.Util;


public class Method {
	/**
	 * �������ݿ�
	 * @param databaseName
	 * @return String
	 */
	public static String createDataBase(String databaseName) {
		File fi = new File("db/"+databaseName);
		if(fi.exists()) {
			return "the database already exists";
		}
		if(fi.mkdirs()) {
			return "create database successfully";	
		}
		return "create database failed";
	}
	
	/**
	 * �������ݱ�
	 * @param tableSql
	 * @return String
	 */
	public static String createTable(String tableSql,String currentDbName) throws IOException{
		//���ɱ�ṹ����
		TableConfig tableconfig = new TableConfig();			
		
		String tableName = tableSql.split(" ")[2];
		tableconfig.setTableName(tableName);
		
		int startColumns = tableSql.indexOf("(");
		int endColumns = tableSql.lastIndexOf(")");
		String sql_tempColumns = tableSql.substring(startColumns+1, endColumns).trim();
	
		String[] sql_column = sql_tempColumns.split(",");
		
		for(String str : sql_column) {
			String[] strTemp = str.split(" ");
			
			//���������������
			if(strTemp[0].equals("primary")) {
				if(strTemp[1].equals("key")) {
					String primaryKey = Util.ridQuotes(strTemp[2]);
					tableconfig.setPrimaryKey(primaryKey);
				}else {
					return "create table syntax error";
				}
			}else {
				//����������
				String col_name = Util.ridQuotes(strTemp[0]);
				String type = strTemp[1];
				boolean isNotNull = str.contains("not null");
				
				int size = 0;
				if(type.equals("int")) {
					size = 4;
				}else if(type.contains("char")) {
					int start = type.indexOf("(");
					int end = type.lastIndexOf(")");
					size = Integer.parseInt(type.substring(start+1, end));
				}else {
					return "create table syntax error";
				}
				
				ColumnConfig cf = new ColumnConfig();
				cf.setCol_name(col_name);
				cf.setType(type);
				cf.setSize(size);
				cf.setNull(isNotNull);
				tableconfig.getCfs().add(cf);
			}
			
		}
		//��ʼ����JSON�����ļ����
		File fl = new File("db/"+currentDbName+"/"+tableconfig.getTableName()+".txt");
		File flConfig = new File("db/"+currentDbName+"/"+tableconfig.getTableName()+".json");
		if(fl.exists() && flConfig.exists()) {
			return "this table alrady exist";
		}else {
			fl.createNewFile();
			flConfig.createNewFile();
			
			String tempColumn = "";
			int num = 0;
			for(ColumnConfig tempCf : tableconfig.getCfs()) {
				num++;
				if(num < tableconfig.getCfs().size()) {
					tempColumn = tempCf.strJson()+",";					
				}else {
					tempColumn = tempCf.strJson();	
				}
			}
			
			String tbInformation = "";
			
			return Util.writeFile(tbInformation, flConfig);
					
		}
	}
	
	
	/**
	 * ��ʾ�������ݿ�
	 * @return String
	 */
	public static String showDatabases(){
		
		File fi = new File("db/");
		String listFiles = "";
		for(File file : fi.listFiles()){
			if(file.isDirectory()){
				listFiles += file.getName()+"\n";
			}
		}
		return listFiles;
	}
	
	/**
	 * ��ʾ���ݿ��еı�
	 * @param dbName
	 * @return String
	 */
	public static String showTables(String dbName){
		if(dbName.equals("/")){
			return "please choose database";
		}
		File fi = new File("db/"+dbName);
		String returnSentence = "";
		for(File file : fi.listFiles()){
			if(file.getName().endsWith(".txt")){
				returnSentence += file.getName().substring(0, file.getName().lastIndexOf("."))+"\n";
			}
		}
		return returnSentence;
	}
	
	/**
	 * �л����ݿ�
	 * @param dbName
	 * @return String
	 */
	public static String changeDatabase(String dbName){
		File fi = new File("db/"+dbName);
		if(!fi.exists()){
			return "database is not exist";
		}
		return "change database OK";
	}
	
	/**
	 * ɾ�����ݿ�
	 * @param dbName
	 * @param currentDbName
	 * @return String
	 */
	public static String dropDatabase(String dbName,String currentDbName){
		File fi = new File("db/"+dbName);
		if(!fi.exists()){
			return "database is not exist";
		}
		if(dbName.equals(currentDbName)){
			return "database is in use";
		}
		deleteFiles(fi);
		return "drop database OK";
	}
	
	/**
	 * ɾ���ļ���Ŀ¼
	 * @param fi
	 */
	public static void deleteFiles(File fi){
		if(fi.listFiles()!=null){
			for(File file : fi.listFiles()){
				if(file.isDirectory()){
					deleteFiles(file);
				}else{
					file.delete();
				}
			}
		}
		fi.delete();
	}
	
	/**
	 * ɾ����
	 * @param tableName
	 * @param currentDbName
	 * @return
	 */
	public static String dropTables(String tableName, String currentDbName){
		File fi = new File("db/"+currentDbName+"/"+tableName+".txt");
		if(!fi.exists()){
			return "this table not exist";
		}
		if(fi.delete()){
			return "drop table OK";
		}
		return "drop table error";
	}
	
	/**
	 * ��ѯ��ǰʹ�õ����ݿ�
	 * @param currentDbName
	 * @return String
	 */
	public static String selectDatabase(String currentDbName){
		if(currentDbName.equals("/")){
			return "no use database";
		}
		return currentDbName;
	}
	
	/**
	 * ��ѯ����ȫ����Ϣ
	 * @param tableName
	 * @param currentDbName
	 * @param flag
	 * @param condition
	 * @return
	 */
	public static String selectAllFromTable(String tableName,String currentDbName) throws IOException{
		File fi = new File("db/"+currentDbName+"/"+tableName+".txt");
		if(!fi.exists()){
			return "This table is not exist";
		}
		
		FileInputStream in = new FileInputStream(fi);
		byte [] readByte = new byte[400];
		int n=0;
		String s="";
		while((n=in.read(readByte, 0, readByte.length))!=-1) {
			String tt = new String(readByte,0,n);
			s += tt;
			System.out.print(tt);
		}
		in.close();
		if(s=="") {
			return "Empty set";
		}
		return s;
	}
	
	/**
	 * ����������ѯ   !!��ֻʵ�ֵ������Ⱥ��ж�
	 * 1������ĳ��ѧ����ѧ�ţ���ѯ���ѧ���������Ϣ���������Ա�����ϵ���������������ƣ�
	 * @param tableName
	 * @param currentDbName
	 * @param flag
	 * @param condition
	 * @return
	 * @throws IOException 
	 */
	public static String selectFlagFromTable(String tableName,String currentDbName,String flag,String condition) throws IOException{
		File fileTxt = new File("db/"+currentDbName+"/"+tableName+".txt");
		File fileJson = new File("db/"+currentDbName+"/"+tableName+".json");
		if(!fileTxt.exists()&&!fileJson.exists()){
			return "This table is not exist";
		}
		
		ArrayList<ColumnData> list = Util.fileToList(fileTxt, fileJson);
		
		ArrayList<Integer> numRow = new ArrayList<Integer>();
		String [] arrayFlag = flag.split(",");
		
		//ʹ��������...
		String [] arrayCondition = condition.split("=");
		//select ѧ��,���� from ѧ���� where ѧ��=200902009;
		//����ѯ������������ʱlist��
		for (ColumnData lis : list) {
			if(lis.getCloumn().equals(arrayCondition[0])) {
				if(lis.getData().equals(arrayCondition[1])) {
					numRow.add(lis.getRow());
				}
			}
		}
		//�����Ӧ���ֶ�
		if(numRow.size()!=0) {
			String a="";
			int j=0;
			for (int i = 0; i <numRow.size(); i++) {
				for (ColumnData lisTemp : list) {
					if(lisTemp.getRow()==numRow.get(i)&& Arrays.asList(arrayFlag).contains(lisTemp.getCloumn())) {
						j++;
						if(j%arrayFlag.length==0) {
							a +=lisTemp.getData()+"\n";							
						}else {
							a +=lisTemp.getData()+"\t";							
						}
					}
				}				
			}
			return a;
		}
		return "no result";
	}
	
	/**
	 * 2������ĳ��ѧ����ѧ�ţ���ѯѡ������λѧ��ȫ���γ̵�ѧ����Ϣ��
	 * @param tableName
	 * @param currentDbName
	 * @param stuNo
	 * @param conditio
	 * @return
	 * @throws IOException
	 */
	public static String selectInfoByCourse(String tableName,String currentDbName,String stuNo) throws IOException{
		File fileTxtCS = new File("db/"+currentDbName+"/ѧ��ѡ�α�.txt");
		File fileJsonCS = new File("db/"+currentDbName+"/ѧ��ѡ�α�.json");
		
		//��ȡ������
		ArrayList<ColumnRow> listRow = Util.fileToListByRow(fileTxtCS, fileJsonCS);
		
//		System.out.println("������"+listRow.size());
		ArrayList<Integer> numRow = new ArrayList<Integer>();
		//����ѯ������������ʱlist��
		for (ColumnRow lis : listRow) {
			if(lis.getCds().get(0).getData().equals(stuNo)) {
				numRow.add(lis.getCds().get(0).getRow());
			}
		}
		//�洢��ѧ�������пγ�
		ArrayList<String> tempCourse = new ArrayList<String>();
		for (Integer integer : numRow) {
			for (ColumnRow columnRow : listRow) {
				if(columnRow.getRowNum()==integer.intValue()) {
					tempCourse.add(columnRow.getCds().get(1).getData());
				}
			}
		}
		
		//�Ƴ�û��ѡ��Щ�γ̵�ѧ��+�����Ƴ�����ѯ���Ǹ�ѧ��
		boolean jud = true;
		boolean tempJud = true;
		while(jud) {
			for (int i = 0; i < listRow.size(); i++) {
				if(stuNo.equals(listRow.get(i).getCds().get(0).getData())){
					listRow.remove(i);
					tempJud=false;
					break;
				}
				if(!tempCourse.contains(listRow.get(i).getCds().get(1).getData())){
					listRow.remove(i);
					tempJud=false;
					break;
				}
			}
			if(jud&&tempJud) {
				jud=false;				
			}
			tempJud = true;
		}
//		System.out.println("�Ƴ����listRow�Ĵ�С��"+listRow.size());
		
		//���ݿ������⣬����Ҫ�����Ƴ�����������
		ArrayList<ColumnRow> row = new ArrayList<ColumnRow>();
		for (int i = 0; i < listRow.size(); i++) {
			if(i==0) {
				row.add(listRow.get(i));
			}else {
				jud = true;
				tempJud = true;
				for (int j = 0; j < row.size(); j++) {
					if(row.get(j).getCds().get(0).getData().equals(listRow.get(i).getCds().get(0).getData())) {
						if(row.get(j).getCds().get(1).getData().equals(listRow.get(i).getCds().get(1).getData())) {
							tempJud=false;
							break;
						}
					}
				}
				if(jud&&tempJud) {
					row.add(listRow.get(i));			
				}
			}
		}
//		System.out.println("�ڶ��β�����row�Ĵ�С��"+row.size());
//		//���
//		int b=0;
//		for (ColumnRow ll : row) {
//			System.out.print(ll.getCds().get(0).getData()+" ");
//			b++;
//			if(b%10==0) {
//				System.out.println(b/10+"�У�");
//			}
//		}
		
		//�ж�ͬʱ��ѡ��Щ�γ̵�ѧ��
		ArrayList<Integer> num = new ArrayList<Integer>();//����ִ���
		
		for (int i = 0; i < row.size(); i++) {
			int tt=0;  
			for(int j=0;j<row.size();j++) {
				if(row.get(j).getCds().get(0).getData().equals(row.get(i).getCds().get(0).getData())){
					tt++;
				}
			}
			num.add(tt);
		}
		//���
//		int aa=0;
//		for (Integer integer : num) {
//			aa++;
//			if(aa%10==0) {
//				System.out.println(aa/10+"�У�");
//			}else {
//				System.out.print(integer+" ");
//			}
//		}
		//�����ִ�����ͬѧ���пγ���Ŀ��ͬ��ѧ��ѧ�Ŵ洢����
		ArrayList<String> tempStus = new ArrayList<String>();//��ѧ��
		
		for (int i = 0; i < num.size(); i++) {
			jud=true;
			if(num.get(i)==tempCourse.size()) {
				if(!tempStus.isEmpty()) {
					for (String string : tempStus) {
						if(string.equals(row.get(i).getCds().get(0).getData())) {
							jud=false;
							break;
						}
					}
				}	
				if(jud) {
					tempStus.add(row.get(i).getCds().get(0).getData());											
				}
			}
		}
		//���
//		for (String string : tempStus) {
//			System.out.println("�ҵ���ѧ����"+string);
//		}
		if(!tempStus.isEmpty()) {
			String result ="ѧ��\t����\t�Ա�\t����ϵ\t�ֻ���\t���Һ�\n";
			for (int i = 0; i < tempStus.size(); i++) {
				result += selectFlagFromTable("ѧ����", currentDbName, "ѧ��,����,�Ա�,����ϵ,�ֻ���,����", "ѧ��="+tempStus.get(i));
			}
			return result;
		}
		return "no result";
	}
	
	/**
	 * 3������ĳ����ʦ�Ĺ��ţ���ѯ�����ϵ�ÿ�ſε�ƽ���ɼ���
	 * @param tableName
	 * @param currentDbName
	 * @param Tno
	 * @return
	 * @throws IOException
	 */
	public static String selectInfoByTno(String tableName,String currentDbName,String Tno) throws IOException{
		File fileTxtTC = new File("db/"+currentDbName+"/��ʦ�ڿα�.txt");
		File fileJsonTC = new File("db/"+currentDbName+"/��ʦ�ڿα�.json");
		
		//��ȡ������
		ArrayList<ColumnRow> listRowTC = Util.fileToListByRow(fileTxtTC, fileJsonTC);
		
		System.out.println("������"+listRowTC.size());
		ArrayList<Integer> numRowTC = new ArrayList<Integer>();
		//����ѯ������������ʱlist��
		for (ColumnRow lis : listRowTC) {
			if(lis.getCds().get(0).getData().equals(Tno)) {
				numRowTC.add(lis.getCds().get(0).getRow());
			}
		}
		//�洢�ý�ʦ���ڵ����пγ�
		ArrayList<String> tempCourse = new ArrayList<String>();
		for (Integer integer : numRowTC) {
			for (ColumnRow columnRow : listRowTC) {
				if(columnRow.getRowNum()==integer.intValue()) {
					tempCourse.add(columnRow.getCds().get(1).getData());
				}
			}
		}
		for (String string : tempCourse) {
			System.out.println("���ڿγ̣�"+string);
		}
		
		/*
		 * �ڶ��β�ѯ����ѯѧ��ѡ�α�
		 */
		File fileTxtCS = new File("db/"+currentDbName+"/ѧ��ѡ�α�.txt");
		File fileJsonCS = new File("db/"+currentDbName+"/ѧ��ѡ�α�.json");
		
		//��ȡ������
		ArrayList<ColumnRow> listRow = Util.fileToListByRow(fileTxtCS, fileJsonCS);
		System.out.println("������"+listRow.size());
		ArrayList<Integer> numRow = new ArrayList<Integer>();
		//����ѯ��������������ʱlist��
		for (ColumnRow lis : listRow) {
			if(lis.getCds().get(0).getData().equals(Tno)) {
				numRow.add(lis.getCds().get(0).getRow());
			}
		}
		
		//�Ƴ�û��ѡ��Щ�γ̵�ѧ��+�����Ƴ�����ѯ���Ǹ�ѧ��
		boolean jud = true;
		boolean tempJud = true;
		while(jud) {
			for (int i = 0; i < listRow.size(); i++) {
				if(Tno.equals(listRow.get(i).getCds().get(0).getData())){
					listRow.remove(i);
					tempJud=false;
					break;
				}
				if(!tempCourse.contains(listRow.get(i).getCds().get(1).getData())){
					listRow.remove(i);
					tempJud=false;
					break;
				}
			}
			if(jud&&tempJud) {
				jud=false;				
			}
			tempJud = true;
		}
		
		//���ݿ������⣬����Ҫ�����Ƴ�����������
		ArrayList<ColumnRow> row = new ArrayList<ColumnRow>();
		
		for (int i = 0; i < listRow.size(); i++) {
			if(i==0) {
				row.add(listRow.get(i));
			}else {
				jud = true;
				tempJud = true;
				for (int j = 0; j < row.size(); j++) {
					if(row.get(j).getCds().get(0).getData().equals(listRow.get(i).getCds().get(0).getData())) {
						if(row.get(j).getCds().get(1).getData().equals(listRow.get(i).getCds().get(1).getData())) {
							tempJud=false;
							break;
						}
					}
				}
				if(jud&&tempJud) {
					row.add(listRow.get(i));			
				}
			}
		}
		for (int i=0;i<row.size();i++) {
			System.out.print(row.get(i).getCds().get(0).getData()+" ");
			System.out.print(row.get(i).getCds().get(1).getData()+" ");
			System.out.println(row.get(i).getCds().get(2).getData()+" ");
		}
		//������ʦ�γ̵�ƽ���ɼ�
		ArrayList<ColumnData> average = new ArrayList<ColumnData>();//��γ�����ƽ����
		
		
		for (String string : tempCourse) {
			ColumnData cd = new ColumnData();
			cd.setCloumn(string);
			average.add(cd);
		}
		for (int i=0;i<row.size();i++) {
			for (ColumnData columnData : average) {
				if(columnData.getCloumn().equals(row.get(i).getCds().get(1).getData())) {
					int a=columnData.getRow()+1;
					columnData.setRow(a);
					int b=Integer.parseInt(row.get(i).getCds().get(2).getData());
					int c=columnData.getData()==null?0:Integer.parseInt(columnData.getData());
					columnData.setData(String.valueOf(b+c));
				}
			}
		}
		
		if(!average.isEmpty()) {
			String result ="�γ̺�\tƽ���ɼ�\n";
			for (ColumnData columnData : average) {
				String r= columnData.getRow()==0?"0":String.valueOf(new DecimalFormat("0.000").format(Double.parseDouble(columnData.getData())/columnData.getRow()));
				result += columnData.getCloumn()+"\t"+r+"\n";
			}
			return result;
		}
		
		return "no result"; 
	}
	
	
	
	
//	public static String dropDatabase3(){
//		
//		return "OK";
//	}
}
