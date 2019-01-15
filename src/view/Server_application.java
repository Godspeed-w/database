package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.CheckSql;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server_application extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;
	
	ServerSocket server;
	ServerThread main_thread;
	ArrayList<WorkThread> totleWorkThread;
	private JTextField textField_totleclient;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_application frame = new Server_application();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Server_application() {
		setTitle("\u6570\u636E\u5E93\u670D\u52A1\u5668");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//start the server
		JButton button = new JButton("\u5F00\u542F\u670D\u52A1");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int port = Integer.parseInt(textField.getText().trim());
				try {
					server = new ServerSocket(port);
					totleWorkThread = new ArrayList<Server_application.WorkThread>();
					main_thread = new ServerThread();
					main_thread.start();
					textArea.append("server is open...\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		button.setBounds(182, 24, 93, 23);
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 123, 414, 247);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);
		
		JLabel label = new JLabel("\u7AEF\u53E3\uFF1A");
		label.setBounds(10, 28, 54, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setText("6666");
		textField.setBounds(56, 25, 93, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		//shutdown server
		JButton btnNewButton = new JButton("\u5173\u95ED\u670D\u52A1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					server.close();
					if(main_thread.isAlive()){
						main_thread.stop();
					}
					textField_totleclient.setText("0");
					textArea.append("server has closed...");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(303, 24, 93, 23);
		contentPane.add(btnNewButton);
		
		JLabel label_1 = new JLabel("\u5728\u7EBF\u5BA2\u6237\u7AEF\u6570\uFF1A");
		label_1.setBounds(10, 75, 93, 15);
		contentPane.add(label_1);
		
		textField_totleclient = new JTextField();
		textField_totleclient.setText("0");
		textField_totleclient.setBounds(108, 72, 79, 21);
		contentPane.add(textField_totleclient);
		textField_totleclient.setColumns(10);
		
		JButton button_1 = new JButton("\u6E05\u7A7A\u663E\u793A");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
			}
		});
		button_1.setBounds(234, 71, 101, 23);
		contentPane.add(button_1);
	}

	//main thread
	class ServerThread extends Thread{
		public void run() {
			while(true){
				try {
					Socket socket = server.accept();
					WorkThread workthread = new WorkThread(socket);
					workthread.start();
					totleWorkThread.add(workthread);
					textField_totleclient.setText(String.valueOf(totleWorkThread.size()));
					textArea.append("client connect success...\n");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	//work thread
	class WorkThread extends Thread{
		private Socket socket;
		public WorkThread(Socket socket){
			this.socket = socket;
		}
		public void run() {
			while(true){
				try {
					
					OutputStream outToClient = socket.getOutputStream();
			        DataOutputStream send = new DataOutputStream(outToClient);
			        InputStream inFromClient = socket.getInputStream();
					DataInputStream receive = new DataInputStream(inFromClient);
					
					String sql = receive.readUTF().trim();
					if(sql.equals("quit")) {
						textArea.append("client close connection...\n");
						totleWorkThread.remove(this);
						textField_totleclient.setText(String.valueOf(totleWorkThread.size()));
						this.stop();
					}
			
					textArea.append("receive:\n"+sql+"\n");
					
					long startTime = System.nanoTime();
					String response = CheckSql.doSql(sql);
					long endTime = System.nanoTime();
					
					send.writeUTF(response+" \n("+((endTime-startTime)/1000000.0)+" ms)");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
