package packageOne;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class TCPClient {
	
	static Socket clientSocket;
	static ClientChatStart clientChat;
	static String serverResponse;
	
	public TCPClient() throws UnknownHostException, IOException{
		int n;
		double x  =  Math.random()*4;
		int y = (int)x;
		if(y==0)
			n=6000;
		else if(y==1)
			n=8081;
		else if(y==2)
			n=8082;
		else
			n=8083;
		
		clientSocket = new Socket("localhost",n);
		
		
		clientChat=new ClientChatStart(clientSocket);
		this.serverResponse="";
		
	}
	
	public static void join(String name) throws UnknownHostException,
			IOException {
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());
		outToServer.writeBytes("joinRequest." + name + "\n");
		DataInputStream inFromServer= new DataInputStream(clientSocket.getInputStream());
		serverResponse=inFromServer.readLine();
	}
	public static void LogOff() throws IOException{
		DataOutputStream outToServer=new DataOutputStream(
				clientSocket.getOutputStream());
		outToServer.writeBytes("LOGOFFALLSERVERS"+"\n");
	}

	public static void getMemberList() throws UnknownHostException, IOException {
		
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());		
		String s = "Server/GetList";
		outToServer.writeBytes(s + "\n");
		DataInputStream inFromServer= new DataInputStream(clientSocket.getInputStream());
		serverResponse=inFromServer.readLine();
	}

	public static void chat(String source, String destination, String Message)
			throws IOException {
		
		String sentence;
		String serverResponse;
			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			String message = source + ".*." + destination + ".*." + Message;
			
			outToServer.writeBytes(message + "\n");
			
		}

	
	public static void startChat() throws UnknownHostException, IOException{
		clientSocket = new Socket("localhost", 6000);
		clientChat=new ClientChatStart(clientSocket);
		new Thread(clientChat).start();
	}
public static void main(String[] args) throws UnknownHostException, IOException {
	startChat();
}
	

}