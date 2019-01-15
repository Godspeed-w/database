package test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class T_writeFile {
	public static void main(String[] args) throws IOException {
		File file = new File("db/temp.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		raf.seek(raf.length());
		raf.write("追加1\n".getBytes());
		raf.write("是非得失qwqw\n".getBytes());
		raf.close();
	}
}	