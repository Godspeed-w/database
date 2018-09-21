package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;

import java.net.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Client_application extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea_sql;
	private JTextField textField_address;
	private JTextField textField_port;
	private JTextArea textArea_message;
	private Socket socket;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					Client_application frame = new Client_application();
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
	public Client_application() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					OutputStream outToServer = socket.getOutputStream();
					DataOutputStream send = new DataOutputStream(outToServer);
					send.writeUTF("quit");
					socket.close();
					textArea_message.append("close connection...\n");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setTitle("My console");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(72, 94, 250, 72);
		contentPane.add(scrollPane);
		
		textArea_sql = new JTextArea();
		textArea_sql.setColumns(5);
		textArea_sql.setLineWrap(true);
		scrollPane.setViewportView(textArea_sql);
		
		JLabel lblNewLabel = new JLabel("\u8F93\u5165\u547D\u4EE4\uFF1A");
		lblNewLabel.setBounds(10, 113, 68, 15);
		contentPane.add(lblNewLabel);
		
		JButton button = new JButton("\u6267\u884C");
		//excute sql 
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputStream outToServer = socket.getOutputStream();
					DataOutputStream send = new DataOutputStream(outToServer);
					InputStream inFromServer = socket.getInputStream();
					DataInputStream recive = new DataInputStream(inFromServer); 
					
					String sql = textArea_sql.getText().trim();
					send.writeUTF(sql);
					System.out.println("add");
					
					if(sql.equals("quit")) {
						socket.close();
						textArea_message.append("close connection...\n");
					}
					
					textArea_message.append("receive:\n"+recive.readUTF());
					System.out.println("add2");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		button.setBounds(332, 143, 93, 23);
		contentPane.add(button);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 176, 435, 242);
		contentPane.add(scrollPane_1);
		
		textArea_message = new JTextArea();
		textArea_message.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane_1.setViewportView(textArea_message);
		
		JLabel label = new JLabel("\u670D\u52A1\u5668\u5730\u5740\uFF1A");
		label.setBounds(24, 10, 79, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u7AEF\u53E3\uFF1A");
		label_1.setBounds(62, 49, 54, 15);
		contentPane.add(label_1);
		
		textField_address = new JTextField();
		textField_address.setText("127.0.0.1");
		textField_address.setBounds(113, 7, 136, 21);
		contentPane.add(textField_address);
		textField_address.setColumns(10);
		
		textField_port = new JTextField();
		textField_port.setText("6666");
		textField_port.setBounds(113, 46, 136, 21);
		contentPane.add(textField_port);
		textField_port.setColumns(10);
		
		JButton button_1 = new JButton("\u5F00\u542F\u8FDE\u63A5");
		//start connection
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String address = textField_address.getText().trim();
				int port = Integer.parseInt(textField_port.getText().trim());
				try {
					socket = new Socket(address,port);
					textArea_message.append("connection successful...\n");
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					textArea_message.append("connection failed...\n");
					e.printStackTrace();
				}
			}
		});
		button_1.setBounds(332, 20, 113, 23);
		contentPane.add(button_1);
		
		//close connection
		JButton button_2 = new JButton("\u5173\u95ED\u8FDE\u63A5");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(socket.isConnected()){
					try {
						OutputStream outToServer = socket.getOutputStream();
						DataOutputStream send = new DataOutputStream(outToServer);
						send.writeUTF("quit");
						socket.close();
						textArea_message.append("close connection...\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		button_2.setBounds(332, 62, 113, 23);
		contentPane.add(button_2);
		
		JButton btnNewButton = new JButton("\u6E05\u7A7A\u663E\u793A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea_message.setText(null);
			}
		});
		btnNewButton.setBounds(332, 95, 113, 23);
		contentPane.add(btnNewButton);
	}
}
