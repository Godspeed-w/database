package wj_test;

import java.io.File;
import java.io.IOException;

import bean.TableConfig;
import dao.Method;

public class T1 {

	public static void main(String[] args) throws IOException {
		String a= Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200905026");
		System.out.println(a);
	}	
}
