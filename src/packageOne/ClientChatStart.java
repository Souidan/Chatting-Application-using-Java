package packageOne;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientChatStart implements Runnable {
	
	static Socket clientSocket;
	String sentence;
	static String serverResponse;
	
	public ClientChatStart(Socket socket){
		
		this.clientSocket = socket;
	}
	
	public void run() {
		try {

			Thread t = new Thread(new Runnable() {

				public void run() {
					while (true) {
						try {
							
							BufferedReader inFromServer = new BufferedReader(
									new InputStreamReader(clientSocket
											.getInputStream()));
							ClientChatStart.serverResponse = inFromServer.readLine();
							System.out.println(serverResponse);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			
			t.start();
			
			while (true) {

			BufferedReader inFromKeyboard = new BufferedReader(
					new InputStreamReader(System.in));

			DataOutputStream outToServer = new DataOutputStream(
					clientSocket.getOutputStream());
			
			sentence = inFromKeyboard.readLine();
			outToServer.writeBytes(sentence + "\n");
			
		}
			}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
