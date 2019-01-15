package dao;

import java.io.IOException;

import util.Util;

public class CheckSql {

	public static String doSql(String sql) throws IOException {
		String[] search = sql.split(" ");
		switch (search[0]) {
		case "1":
			return Method.selectNo1(search[1]);
		case "2":
			return Method.selectNo2(search[1]);
		case "3":
			return Method.selectNo3(search[1]);
		case "4":
			return Method.selectNo4();
		case "5":
			return Method.selectNo5();
		case "6":
			return Method.selectNo6();
		case "7":
			return Method.selectNo7();
		case "8":
			return Method.selectNo8();
		case "9":
			return Method.selectNo9(search[1]);
		case "10":
			return Method.selectNo10(search[1]);
		case "21":
			return Method.selectNoSecond_1(search[1], search[2], search[3]);
		case "22":
			return Method.selectNoSecond_2(search[1], search[2], search[3], search[4], search[5]);
		}
		return "sql syntax error";
	}
}
