package wj_test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Test_json2 {
	public static void main(String[] args) throws IOException {
		//创建新的json文件
		
		FileWriter fw = new FileWriter(new File("db/aa/aa.json"));
		BufferedWriter bf = new BufferedWriter(fw);
		
		String json = "{\"name\":\"军军\",\"pwd\":\"123\"}";
		bf.write(json);
		bf.close();
		fw.close();
		
		System.out.println(json);
		
	}
}
