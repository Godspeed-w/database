package dao;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import bean.ColumnConfig;
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
	 * 根据条件查询
	 * @param tableName
	 * @param currentDbName
	 * @param flag
	 * @param condition
	 * @return
	 * @throws IOException 
	 */
	public static String selectFlagFromTable(String tableName,String currentDbName,String flag,String condition) throws IOException{
		File fi = new File("db/"+currentDbName+"/"+tableName+".txt");
		if(!fi.exists()){
			return "This table is not exist";
		}
		
		FileInputStream in = new FileInputStream(fi);
		byte [] readByte = new byte[1];
		int n=0;
		String s="";
		while((n=in.read(readByte, 0, readByte.length))!=-1) {
			String tt = new String(readByte,0,n);
			s += tt;
			System.out.print(tt);
		}
		in.close();
		return "OK";
	}
	
//	public static String dropDatabase3(){
//		
//		return "OK";
//	}
}
