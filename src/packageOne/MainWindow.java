package packageOne;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.*;

public class MainWindow extends JFrame implements ActionListener,MouseListener{
	JPanel panel;
	JPanel membersList;
	ArrayList<JLabel> membersArrayList;
	JLabel onlineUsers;
	TCPClient client;
	String clientName;
	JButton refresh;
	public MainWindow(TCPClient client,String clientName) throws UnknownHostException, IOException{
		
		this.client=client;
		this.clientName=clientName;
		this.membersArrayList=new ArrayList();
		this.setSize(900, 600);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel=new JPanel();
		panel.setBounds(0, 0, 900, 578);
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		onlineUsers=new JLabel("Hi "+clientName+", Here's a list of the online Users");
		onlineUsers.setBounds(50, 50, 800, 50);
		onlineUsers.setForeground(Color.RED);
		onlineUsers.setFont(new Font("Courier New", Font.BOLD, 23));
		panel.add(onlineUsers);
		membersList=new JPanel();
		membersList.setBounds(100,130,450,400);
		membersList.setLayout(null);
		membersList.setBackground(Color.black);
		membersList.setBorder(BorderFactory.createLineBorder(Color.gray, 3));
		panel.add(membersList);
		refresh=new JButton("Refresh");
		refresh.setBounds(570, 130, 75, 35);
		refresh.addActionListener(this);
		panel.add(refresh);
		displayMemberList();
		this.add(panel);
		this.revalidate();
		this.repaint();
	}
	
	public void displayMemberList() throws UnknownHostException, IOException{
		client.getMemberList();
		String response=client.serverResponse;
		String [] members = response.split(",");
		for(int i=0;i<members.length;i++){
			JLabel name=new JLabel(members[i]);
			name.setForeground(Color.green);
			name.setBounds(20, 40*i+20, 500, 20);
			name.setFont(new Font("Courier New", Font.BOLD, 18));
			name.addMouseListener(this);
			membersList.add(name);
			membersArrayList.add(name);
		}
	}
	public void refresh() throws UnknownHostException, IOException{
		System.out.println("wasal wasal");
		membersList.removeAll();
		membersArrayList.clear();
		displayMemberList();
		revalidate();
		repaint();
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		MainWindow m=new MainWindow(new TCPClient(),"slim");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (membersArrayList.contains(e.getSource())){
			JLabel l=(JLabel) e.getSource();
			String reciever=l.getText();
			new ChatWindow(client, clientName, reciever);
		}
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
		if(e.getSource()==refresh){
			try {
				refresh();
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
