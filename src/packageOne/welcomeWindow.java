package packageOne;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class welcomeWindow extends JFrame implements ActionListener{
    JPanel panel;
    JButton connect;
	public welcomeWindow(){
		this.setSize(800, 400);
		this.setVisible(true);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		panel=new JPanel();
		panel.setBounds(0, 0, 800, 378);
		panel.setBackground(Color.BLACK);
		panel.setVisible(true);
		panel.setLayout(null);
		connect=new JButton("CONNECT");
		connect.setBounds(350,220 , 100, 35);
		connect.addActionListener(this);
		panel.add(connect);
		this.add(panel);
		this.revalidate();
		this.repaint();
		
	}
public static void main(String[] args) {
	welcomeWindow w=new welcomeWindow();
}
@Override
public void actionPerformed(ActionEvent e) {
	if (e.getSource()==this.connect){
		try {
			new JoiningWindow();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		dispose();
	}
}
}

