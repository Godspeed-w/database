package dao;

public class Checksql {
	public static String doSql(String sql) {
		String [] sql_split = sql.split(" ");
		for (String string : sql_split) {
			System.out.println(string);
		}
		
		if(sql_split[0].equals("create")) {
			if(sql_split[1].equals("database")) {
				return Method.createDataBase(sql_split[2]);
			}
		}
		return "OK";
	}
}
