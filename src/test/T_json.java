package test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
public class T_json {
		public static void main(String[] args) throws IOException {
			
			//����json�ļ�,����һ
			FileReader fr = new FileReader("db/��ʦ��.json");
			BufferedReader br = new BufferedReader(fr);
			String str = br.readLine();
			String str1 = "";
			while(str!=null) {
				str1 += str;
				str = br.readLine();
			}
			
			JSONObject jo = new JSONObject(str1);
			System.out.println("����json\n"+jo.toString());
			
			
			JSONArray jo2 = jo.getJSONArray("column");
			System.out.println("shuzu \n"+jo2.toString());
			System.out.println("������Ŀ������"+jo2.length());
			System.out.println(jo2.get(0).toString());
			
			for (int i = 0; i < jo2.length(); i++) {
				if(jo2.get(i).toString().equals("�ֻ���")) {
					System.out.println("Location:"+(i+1));
				}
			}
			
			System.out.println("*************************");
			//����json�ļ�,������
			JSONTokener jt = new JSONTokener(new FileReader("db/��ʦ��.json"));
			
			JSONObject jo11 = (JSONObject)jt.nextValue();
			System.out.println(jo11.toString());
			System.out.println(jo11.getString("tableName"));
		}
}
