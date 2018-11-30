package wj_test;

import java.io.IOException;

import dao.Method;

public class T2 {

	public static void main(String[] args) throws IOException {
//		String b= Method.selectFlagFromTable("教师表", "school", "编号,姓名,职称,工资", "编号=T008");
//		System.out.println(b);
//		String b= Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "课程号=C020");
//		System.out.println(b);
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200907020"));
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200904003"));
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200901016"));
//		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200905026"));
		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200904003"));
		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200905055"));
		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200901004"));
		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200901026"));
		System.out.println(Method.selectFlagFromTable("学生选课表", "school", "学号,课程号,成绩", "学号=200902013"));
		String a=Method.selectInfoByCourse("学生选课表", "school","200904003");
		System.out.println(a);
	}

}
