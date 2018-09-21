package wj_test;
import java.io.*;

public class Test_io {
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		
		File d = new File("db/aa/text.txt");
		
//		d.mkdir();
//		d.createNewFile();
		FileReader fr = new FileReader(d);
		BufferedReader br = new BufferedReader(fr);
		
		FileWriter fw = new FileWriter("db/aa/copy.txt");

		String str3="";
		int i=0;
		while(str3!=null) {
			i++;
			str3 = br.readLine();
			System.out.println(str3);
			fw.write(str3+"\n");
			fw.flush();
		}
		br.close();
		fr.close();
		
		
		long endTime = System.nanoTime();
		System.out.println(d.toURI());
		System.out.println(str3);
		System.out.println(i);
		System.out.println("创建成功"+((endTime-startTime)/100000.0)+"ms");
}
}