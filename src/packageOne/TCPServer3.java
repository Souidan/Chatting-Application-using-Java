package packageOne;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer3 {
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<Socket> usersockets = new ArrayList<Socket>();
	
	
	
	public static void main(String[] args) throws Exception {

		ServerSocket socket = new ServerSocket(8082);
		while (true) {
			Socket connectionSocket = socket.accept();
			Thread t = new Thread(new RunnableServerThree(connectionSocket));
			t.start();
		}

	}
}
