package packageOne;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TCPServer {
	static ArrayList<String> users = new ArrayList<String>();
	static ArrayList<Socket> usersockets = new ArrayList<Socket>();
	static ArrayList<String> slave1users =  new ArrayList<String>();
	static ArrayList<String> slave2users =  new ArrayList<String>();
	static ArrayList<String> slave3users =  new ArrayList<String>();

	
	
	public static void main(String[] args) throws Exception {

		ServerSocket socket = new ServerSocket(6000);
		while (true) {
			Socket connectionSocket = socket.accept();
			Thread t = new Thread(new RunnableServer(connectionSocket));
			t.start();
		}

	}
}
