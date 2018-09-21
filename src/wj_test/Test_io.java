package wj_test;
import java.io.File;
import java.io.IOException;

public class Test_io {
	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		
		File d = new File("db/aa/text.txt");
		
//		d.mkdir();
		d.createNewFile();
		long endTime = System.nanoTime();
		System.out.println(d.toURI());
		System.out.println("创建成功"+((endTime-startTime)/100000.0)+"ms");
}
}