package wj_test;
import java.net.*;
import java.io.*;
 
public class Test_server extends Thread
{
   private ServerSocket serverSocket;
   
   public Test_server(int port) throws IOException
   {
      serverSocket = new ServerSocket(port);
   }
 
   public void run()
   {
      while(true)
      {
         try
         {
            System.out.println("�ȴ�Զ�����ӣ��˿ں�Ϊ��" + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            System.out.println("Զ��������ַ��" + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("лл�����ң�" + server.getLocalSocketAddress() + "\nGoodbye!");
            server.close();
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   public static void main(String [] args)
   {
      int port = 6666;
      try
      {
         Thread t = new Test_server(port);
         t.run();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}
