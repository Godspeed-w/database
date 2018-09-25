package wj_test;
import java.net.*;
import java.io.*;

public class Test_client{
	
	public static void main(String[] args) {
		try {
			Socket client = new Socket("127.0.0.1",6666);
			OutputStream outToServer = client.getOutputStream();
	        DataOutputStream Send = new DataOutputStream(outToServer);
	        InputStream inFromServer = client.getInputStream();
	        DataInputStream Recive = new DataInputStream(inFromServer); 
	        
	         
			System.out.println("远程主机地址：" + client.getRemoteSocketAddress());
			
			Send.writeUTF("Hello from " + client.getLocalSocketAddress());
	         
	         System.out.println("服务器响应： " + Recive.readUTF());
	         
	         client.close();
	         
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

