package wj_test;

import java.io.File;
import java.io.IOException;

import bean.TableConfig;
import dao.Method;

public class T1 {

	public static void main(String[] args) throws IOException {
		String a= Method.selectFlagFromTable("ѧ��ѡ�α�", "school", "ѧ��,�γ̺�,�ɼ�", "ѧ��=200905026");
		System.out.println(a);
	}	
}
