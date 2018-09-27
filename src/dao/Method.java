package dao;

import java.awt.List;
import java.io.File;
import java.util.ArrayList;

import bean.ColumnConfig;
import bean.TableConfig;
import util.TestUtil;


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
	public static String createTable(String tableSql){
		//生成表结构对象
		TableConfig tableconfig = new TableConfig();			
		
		String tableName = tableSql.split(" ")[2];
		tableconfig.setTableName(tableName);
		
		int startColumns = tableSql.indexOf("(");
		int endColumns = tableSql.lastIndexOf(")");
		String sql_tempColumns = tableSql.substring(startColumns+1, endColumns).trim();
		System.out.println(sql_tempColumns);
		String[] sql_column = sql_tempColumns.split(",");
		TestUtil.showArray("拆分出来的含列的全部数据：",sql_column);
		
		for(String str : sql_column) {
			String[] strTemp = str.split(" ");
			
			//处理有主键的情况
			if(strTemp[0].equals("primary")) {
				if(strTemp[1].equals("key")) {
					
				}else {
					return "create table syntax error";
				}
			}else {
				//无主键的列
				String col_name = strTemp[0];
				String type = strTemp[2];
				boolean isNotNull = str.matches("not null");
				
				int size = 4;
				if(type.equals("int")) {
					size = 4;
				}else if(type.matches("char")) {
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
		
//		String[] sql_splitBySpace = tableSql.split(" ");
//		String tableName = sql_splitBySpace[2];
//		String[] columns = null;
		
		
		
		return "OK";
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
	
	
//	public static String dropDatabase3(){
//		
//		return "OK";
//	}
}
