package packageOne;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class JoiningWindow extends JFrame implements ActionListener,MouseListener{

	JPanel panel;
	JTextField name;
	JButton join;
	JButton start;
	TCPClient client;
	JLabel serverResponse;
	
	public JoiningWindow() throws UnknownHostException, IOException {
		
		client=new TCPClient();
		
		
		this.setSize(800, 300);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel=new JPanel();
		panel.setBounds(0, 0, 800, 278);
		panel.setBackground(Color.BLACK);
		panel.setVisible(true);
		panel.setLayout(null);		
		name= new JTextField();
		name.setBounds(200, 170, 200, 35);
		panel.add(name);
		join=new JButton("Join");
		join.setBounds(450,170 , 75, 35);
		join.addActionListener(this);
		start=new JButton("Start");
		start.setBounds(600, 220, 75, 35);
		start.setVisible(false);
		start.addActionListener(this);
		panel.add(join);
		panel.add(start);
		serverResponse=new JLabel();
		serverResponse.setBounds(200,220, 400, 50);
		serverResponse.setText("jhghjhkhgjfjkj");
		serverResponse.setFont(new Font("Courier New", Font.BOLD, 17));
		serverResponse.setForeground(Color.GREEN);
		serverResponse.setVisible(false);
		panel.add(serverResponse);
		this.setResizable(false);
		this.add(panel);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.revalidate();
		this.repaint();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		JoiningWindow a=new JoiningWindow();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.join){
			try {
				String enteredName=this.name.getText();
				client.join(enteredName);
				serverResponse.setText(client.serverResponse);
				serverResponse.setVisible(true);
				if (serverResponse.getText().equals("You can now chat with other members"))
				start.setVisible(true);
				else start.setVisible(false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getSource()==this.start){
			try {
				new MainWindow(client, name.getText());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dispose();
		}
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
	
}
