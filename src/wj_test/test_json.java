package wj_test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONTokener;

public class test_json {
	
	public static void main(String[] args) throws IOException {
		
		//解析json文件,方法一
		FileReader fr = new FileReader("db/aa/tt.json");
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		String str1 = "";
		while(str!=null) {
			str1 += str;
			str = br.readLine();
		}
		
		JSONObject jo = new JSONObject(str1);
		System.out.println("整个json\n"+jo.toString());
		
		String name = jo.getString("name");
		int pwd = jo.getInt("pwd");
		System.out.println("name:"+name+",pwd:"+pwd);
		
		JSONObject jo1 = jo.getJSONObject("total");
		System.out.println("total \n"+jo1.toString());
		System.out.println(jo1.getString("name")+"  "+ jo1.getString("pwd"));
		
		
		JSONArray jo2 = jo.getJSONArray("shuzu");
		System.out.println("shuzu \n"+jo2.toString());
		System.out.println("数组项目个数："+jo2.length());
		System.out.println(jo2.get(0).toString());
		
		//解析json文件,方法二
		JSONTokener jt = new JSONTokener(new FileReader("db/aa/tt.json"));
		
		JSONObject jo11 = (JSONObject)jt.nextValue();
		System.out.println(jo11.toString());
		System.out.println(jo11.getString("name"));
	}
}
