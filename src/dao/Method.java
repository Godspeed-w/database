package dao;

import java.io.File;

public class Method {
	
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
}
