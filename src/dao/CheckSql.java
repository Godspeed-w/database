package dao;

import bean.SqlInformation;

public class CheckSql {
	
	public static String doSql(String sql,SqlInformation sqlInfo) {
		if(sql.endsWith(";")){
			sql = sql.substring(0, sql.indexOf(";")).trim();
		}else{
			return "missing ';'";
		}
		String [] sql_split = sql.split(" ");
		for (String string : sql_split) {
			System.out.print(string+" ");
		}
		System.out.println();
		
		switch(sql_split[0]){
			case "create":
				if(sql_split[1].equals("database")) {
					return Method.createDataBase(sql_split[2]);
				}
				if(sql_split[1].equals("tables")){
					
				}
				break;
			case "show":
				if(sql_split[1].equals("databases")){
					return Method.showDatabases();
				}
				if(sql_split[1].equals("tables")){
					return Method.showTables(sqlInfo.getDbName());
				}
				break;
			case "use":
				sqlInfo.setDbName(sql_split[1]);
				return Method.changeDatabase(sqlInfo.getDbName());
			case "drop":
				if(sql_split[1].equals("database")){
					return Method.dropDatabase(sql_split[2], sqlInfo.getDbName());
				}
				if(sql_split[1].equals("table")){
					return Method.dropTables(sql_split[2], sqlInfo.getDbName());
				}
				break;
			case "insert":
				break;
			case "delete":
				break;
			case "update":
				break;
			case "select":
				if(sql_split[1].equals("database()")){
					return Method.selectDatabase(sqlInfo.getDbName());
				}
				break;
		}
		return "sql syntax error";
	}
}
