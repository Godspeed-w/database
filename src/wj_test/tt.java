package wj_test;
import java.io.*;
 
public class tt {
	 public static void main(String args[]) throws IOException {
//	       
		 FileInputStream in = new FileInputStream("db/school/ΩÃ ¶±Ì.txt");
//		 int a;
//		 int b = 1;
//		 while((a=in.read())!=-1) {
//			 if(a <= 0xf) {
//				 System.out.print("0");
//			 }
//			 System.out.print(Integer.toHexString(a)+" ");
//			 if(b++%10==0) {
//				 System.out.println();
//			 }
//		 }
//		 in.close();
		 
		 byte [] readByte = new byte[400]; 
		 int a ;
		 int i = 1;
		 while((a=in.read(readByte, 0, readByte.length))!=-1) {
			 for(int j = 0;j < a;j++) {
//				 if(readByte[j] <= 0xf) {
//					 System.out.print("0");
//				 }
//				 System.out.print(Integer.toHexString(readByte[j]&0xff)+" ");
				 String tt = new String(readByte, "GBK");
				 System.out.println(tt);
				 
//				 readByte.toString();
			 }
		 }
	 }

}