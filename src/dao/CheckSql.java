package dao;

import java.io.IOException;

import bean.SqlInformation;
import util.Util;

public class CheckSql {
	
	public static String doSql(String sql,SqlInformation sqlInfo) throws IOException {
		if(sql.endsWith(";")){
			sql = sql.substring(0, sql.indexOf(";")).trim();
		}else{
			return "missing ';'";
		}
		String [] sql_split = sql.split(" ");
//		String currentDbName = sqlInfo.getDbName();
		String currentDbName = "school";
		
		Util.showArray("服务器收到的Sql：",sql_split);
		switch(sql_split[0]){
			case "create":
				if(sql_split[1].equals("database")) {
					return Method.createDataBase(sql_split[2]);
				}
				if(sql_split[1].equals("table")){
					try {
						return Method.createTable(sql,currentDbName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			case "show":
				if(sql_split[1].equals("databases")){
					return Method.showDatabases();
				}
				if(sql_split[1].equals("tables")){
					return Method.showTables(currentDbName);
				}
				break;
			case "use":
				sqlInfo.setDbName(sql_split[1]);
				return Method.changeDatabase(currentDbName);
			case "drop":
				if(sql_split[1].equals("database")){
					return Method.dropDatabase(sql_split[2], currentDbName);
				}
				if(sql_split[1].equals("table")){
					return Method.dropTables(sql_split[2], currentDbName);
				}
				break;
			case "insert":
				break;
			case "delete":
				break;
			case "update":
				break;
			case "select":
				String flag = sql_split[1];
				String tablename = sql_split[3];
				if(sql_split[1].equals("database()")){
					return Method.selectDatabase(currentDbName);
				}
				if(sql_split[1].equals("*")){
					return Method.selectAllFromTable(sql_split[3],currentDbName);
				}
				if(!sql_split[1].equals("*")){
					return Method.selectFlagFromTable(sql_split[3], currentDbName, flag, sql_split[5]);
				}
				break;
		}
		return "sql syntax error";
	}
}
