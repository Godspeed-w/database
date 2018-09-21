package wj_test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONObject;
import org.json.JSONString;

public class test_json {
	
	public static void main(String[] args) throws FileNotFoundException {
		JSONObject jo = new JSONObject(new FileReader(new File("db/aa/tt.json")));
//		JSONString js =
//		System.out.println(jo.toString());
	}
}
