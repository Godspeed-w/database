package test;

import bean.Ram;
import util.Util;

public class T_Ram {
	public static void main(String[] args) {
		for(int i=0;i<Ram.a.length;i++) {
			System.out.print(Ram.a[i]+" ");
		}
		System.out.println();
		Util.init();
		for(int i=0;i<Ram.a.length;i++) {
			System.out.print(Ram.a[i]+" ");
		}
		System.out.println();
		System.out.println(new String(Ram.a));
	}
}
