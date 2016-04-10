package packageOne;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer2 {
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<Socket> usersockets = new ArrayList<Socket>();
	
	
	
	public static void main(String[] args) throws Exception {

		ServerSocket socket = new ServerSocket(8081);
		while (true) {
			Socket connectionSocket = socket.accept();
			Thread t = new Thread(new RunnableServerTwo(connectionSocket));
			t.start();
		}

	}
}
