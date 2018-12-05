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
	 * 创建数据库
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
	 * 创建数据表
	 * @param tableSql
	 * @return String
	 */
	public static String createTable(String tableSql,String currentDbName) throws IOException{
		//生成表结构对象
		TableConfig tableconfig = new TableConfig();			
		
		String tableName = tableSql.split(" ")[2];
		tableconfig.setTableName(tableName);
		
		int startColumns = tableSql.indexOf("(");
		int endColumns = tableSql.lastIndexOf(")");
		String sql_tempColumns = tableSql.substring(startColumns+1, endColumns).trim();
	
		String[] sql_column = sql_tempColumns.split(",");
		
		for(String str : sql_column) {
			String[] strTemp = str.split(" ");
			
			//处理有主键的情况
			if(strTemp[0].equals("primary")) {
				if(strTemp[1].equals("key")) {
					String primaryKey = Util.ridQuotes(strTemp[2]);
					tableconfig.setPrimaryKey(primaryKey);
				}else {
					return "create table syntax error";
				}
			}else {
				//无主键的列
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
		//开始创建JSON配置文件与表
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
	 * 显示所有数据库
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
	 * 显示数据库中的表
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
	 * 切换数据库
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
	 * 删除数据库
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
	 * 删除文件及目录
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
	 * 删除表
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
	 * 查询当前使用的数据库
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
	 * 查询表中全部信息
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
	 * 根据条件查询   !!！只实现单条件等号判断
	 * 1、给定某个学生的学号，查询这个学生的相关信息（姓名，性别，所在系名，所在寝室名称）
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
		
		//使用正则表达...
		String [] arrayCondition = condition.split("=");
		//select 学号,姓名 from 学生表 where 学号=200902009;
		//将查询到的数存在临时list中
		for (ColumnData lis : list) {
			if(lis.getCloumn().equals(arrayCondition[0])) {
				if(lis.getData().equals(arrayCondition[1])) {
					numRow.add(lis.getRow());
				}
			}
		}
		//输出相应的字段
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
	 * 2、给定某个学生的学号，查询选修了这位学生全部课程的学生信息。
	 * @param tableName
	 * @param currentDbName
	 * @param stuNo
	 * @param conditio
	 * @return
	 * @throws IOException
	 */
	public static String selectInfoByCourse(String tableName,String currentDbName,String stuNo) throws IOException{
		File fileTxtCS = new File("db/"+currentDbName+"/学生选课表.txt");
		File fileJsonCS = new File("db/"+currentDbName+"/学生选课表.json");
		
		//获取行数组
		ArrayList<ColumnRow> listRow = Util.fileToListByRow(fileTxtCS, fileJsonCS);
		
//		System.out.println("行数："+listRow.size());
		ArrayList<Integer> numRow = new ArrayList<Integer>();
		//将查询到的数存在临时list中
		for (ColumnRow lis : listRow) {
			if(lis.getCds().get(0).getData().equals(stuNo)) {
				numRow.add(lis.getCds().get(0).getRow());
			}
		}
		//存储该学生的所有课程
		ArrayList<String> tempCourse = new ArrayList<String>();
		for (Integer integer : numRow) {
			for (ColumnRow columnRow : listRow) {
				if(columnRow.getRowNum()==integer.intValue()) {
					tempCourse.add(columnRow.getCds().get(1).getData());
				}
			}
		}
		
		//移除没有选这些课程的学生+并且移除所查询的那个学生
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
//		System.out.println("移除后的listRow的大小："+listRow.size());
		
		//数据库有问题，还需要继续移除。。。。。
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
//		System.out.println("第二次操作后row的大小："+row.size());
//		//输出
//		int b=0;
//		for (ColumnRow ll : row) {
//			System.out.print(ll.getCds().get(0).getData()+" ");
//			b++;
//			if(b%10==0) {
//				System.out.println(b/10+"行：");
//			}
//		}
		
		//判断同时都选这些课程的学生
		ArrayList<Integer> num = new ArrayList<Integer>();//存出现次数
		
		for (int i = 0; i < row.size(); i++) {
			int tt=0;  
			for(int j=0;j<row.size();j++) {
				if(row.get(j).getCds().get(0).getData().equals(row.get(i).getCds().get(0).getData())){
					tt++;
				}
			}
			num.add(tt);
		}
		//输出
//		int aa=0;
//		for (Integer integer : num) {
//			aa++;
//			if(aa%10==0) {
//				System.out.println(aa/10+"行：");
//			}else {
//				System.out.print(integer+" ");
//			}
//		}
		//将出现次数与同学所有课程数目相同的学生学号存储起来
		ArrayList<String> tempStus = new ArrayList<String>();//存学号
		
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
		//输出
//		for (String string : tempStus) {
//			System.out.println("找到的学生："+string);
//		}
		if(!tempStus.isEmpty()) {
			String result ="学号\t姓名\t性别\t所在系\t手机号\t寝室号\n";
			for (int i = 0; i < tempStus.size(); i++) {
				result += selectFlagFromTable("学生表", currentDbName, "学号,姓名,性别,所在系,手机号,寝室", "学号="+tempStus.get(i));
			}
			return result;
		}
		return "no result";
	}
	
	/**
	 * 3、给定某个教师的工号，查询其所上的每门课的平均成绩。
	 * @param tableName
	 * @param currentDbName
	 * @param Tno
	 * @return
	 * @throws IOException
	 */
	public static String selectInfoByTno(String tableName,String currentDbName,String Tno) throws IOException{
		File fileTxtTC = new File("db/"+currentDbName+"/教师授课表.txt");
		File fileJsonTC = new File("db/"+currentDbName+"/教师授课表.json");
		
		//获取行数组
		ArrayList<ColumnRow> listRowTC = Util.fileToListByRow(fileTxtTC, fileJsonTC);
		
		System.out.println("行数："+listRowTC.size());
		ArrayList<Integer> numRowTC = new ArrayList<Integer>();
		//将查询到的数存在临时list中
		for (ColumnRow lis : listRowTC) {
			if(lis.getCds().get(0).getData().equals(Tno)) {
				numRowTC.add(lis.getCds().get(0).getRow());
			}
		}
		//存储该教师教授的所有课程
		ArrayList<String> tempCourse = new ArrayList<String>();
		for (Integer integer : numRowTC) {
			for (ColumnRow columnRow : listRowTC) {
				if(columnRow.getRowNum()==integer.intValue()) {
					tempCourse.add(columnRow.getCds().get(1).getData());
				}
			}
		}
		for (String string : tempCourse) {
			System.out.println("教授课程："+string);
		}
		
		/*
		 * 第二次查询，查询学生选课表
		 */
		File fileTxtCS = new File("db/"+currentDbName+"/学生选课表.txt");
		File fileJsonCS = new File("db/"+currentDbName+"/学生选课表.json");
		
		//获取行数组
		ArrayList<ColumnRow> listRow = Util.fileToListByRow(fileTxtCS, fileJsonCS);
		System.out.println("行数："+listRow.size());
		ArrayList<Integer> numRow = new ArrayList<Integer>();
		//将查询到的行数存在临时list中
		for (ColumnRow lis : listRow) {
			if(lis.getCds().get(0).getData().equals(Tno)) {
				numRow.add(lis.getCds().get(0).getRow());
			}
		}
		
		//移除没有选这些课程的学生+并且移除所查询的那个学生
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
		
		//数据库有问题，还需要继续移除。。。。。
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
		//计算老师课程的平均成绩
		ArrayList<ColumnData> average = new ArrayList<ColumnData>();//存课程名和平均分
		
		
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
			String result ="课程号\t平均成绩\n";
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
