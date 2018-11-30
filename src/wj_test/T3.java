package wj_test;

import java.io.IOException;

import dao.Method;

public class T3 {
	public static void main(String[] args) throws IOException {
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200905055"));
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200901004"));
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200901026"));
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200902013"));
		String a=Method.selectInfoByTno("教师授课表", "school","T001");
		System.out.println(a);
	}
}
