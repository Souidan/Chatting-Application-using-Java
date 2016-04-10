package packageOne;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ChatWindow extends JFrame implements ActionListener,MouseListener{
	TCPClient client;
	public static String clientName;
	public static String recieverName;
	JPanel panel;
	JLabel reciever;
	JPanel chatContent;
	JButton send;
	JTextField inputText;
	public static ArrayList<String>messages;
	public ChatWindow(TCPClient client, String clientName,String recieverName){
		
		this.client=client;
		this.clientName=clientName;
		this.recieverName=recieverName;
		this.setSize(800, 500);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		messages=new ArrayList<>();
		panel=new JPanel();
		panel.setBounds(0, 0, 800, 478);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		this.add(panel);
		
		reciever=new JLabel("Chat with: "+recieverName);
		reciever.setBounds(40, 20, 700, 50);
		reciever.setForeground(Color.WHITE);
		reciever.setFont(new Font("Courier New", Font.BOLD, 25));
		panel.add(reciever);
		
		chatContent=new JPanel();
		chatContent.setLayout(null);
		chatContent.setBounds(30, 80, 700, 230);
		chatContent.setBackground(Color.black);
		chatContent.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		
		
		
		panel.add(chatContent);
		
		inputText=new JTextField();
		inputText.setBounds(30, 320, 620, 100);
		inputText.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 4));
		inputText.setBackground(Color.GRAY);
		panel.add(inputText);
		
		send=new JButton("Send");
		send.setBounds(658, 320, 75, 35);
		send.addActionListener(this);
		panel.add(send);
		Thread t=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					String s;
				try {
					BufferedReader inFromServer = new BufferedReader(new InputStreamReader(client.clientSocket.getInputStream()));
					s = inFromServer.readLine();
					if(s!=null){
						int index=s.indexOf(':');
						System.out.println(s);
						String s1=s.substring(0,index);
						//System.out.println(s1);
						if (s1.equalsIgnoreCase("FROM "+recieverName)){
							messages.add(s);
						}
						}
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					
				
				
				refreshChatContent();
				//System.out.println(client.clientChat.serverResponse);
				//System.out.println(messages);
				}	
			}
		});
		t.start();
		
		this.revalidate();
		this.repaint();
	}

	public static void main(String[] args) throws UnknownHostException, IOException {

	}
	public void refreshChatContent(){
		chatContent.removeAll();
		for(int i=0;i<messages.size();i++){
			JLabel message=new JLabel(messages.get(i));
			message.setForeground(Color.red);
			message.setBounds(20, 40*i+20, 500, 20);
			message.setFont(new Font("Courier New", Font.BOLD, 12));
			chatContent.add(message);
		}
		revalidate();
		repaint();
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==send){
			try {
				System.out.println("1");
				client.chat(clientName, recieverName, inputText.getText());
				
				messages.add("FROM "+ clientName+": "+inputText.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			refreshChatContent();
		}
	}

}
