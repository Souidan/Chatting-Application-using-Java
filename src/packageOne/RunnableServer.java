package packageOne;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RunnableServer implements Runnable {
	Socket connectionsocket;

	public RunnableServer(Socket s) {
		connectionsocket = s;

	}

	public void run() {
		String clientSentence;
		try {
			while (true) {

				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionsocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(
						connectionsocket.getOutputStream());

				clientSentence = inFromClient.readLine();

				if (clientSentence.length() >= 11
						&& clientSentence.substring(0, 11)
								.equals("joinRequest")) {
					if (TCPServer.users.contains(clientSentence.substring(12,
							clientSentence.length())))

						outToClient.writeBytes("Name is already taken" + "\n");
					else {
						TCPServer.users.add(clientSentence.substring(12,
								clientSentence.length()));
						TCPServer.usersockets.add(connectionsocket);
						outToClient
								.writeBytes("You can now chat with other members"
										+ "\n");
					}

				} else {
					if (clientSentence.equals("Server/GetList")) {
						System.out.println("WASSAL");	
						String lista = "";
						// System.out.println("i am in lista");
						for (int i = 0; i < TCPServer.users.size(); i++) {

							lista = lista + TCPServer.users.get(i) + ",";
						}
						for (int i = 0; i < TCPServer.slave1users.size(); i++) {
							lista = lista + TCPServer.slave1users.get(i) + ",";

						}
						for (int i = 0; i < TCPServer.slave2users.size(); i++) {
							lista = lista + TCPServer.slave2users.get(i) + ",";

						}
						for (int i = 0; i < TCPServer.slave3users.size(); i++) {
							lista = lista + TCPServer.slave3users.get(i) + ",";

						}
						lista = lista.substring(0, lista.length() - 1);
						outToClient.writeBytes(lista + "\n");

					}

					else {

						if (clientSentence.length() >= 19
								&& clientSentence.substring(0, 16).equals(
										"FromSlaveRequest")) {
							String[] message = clientSentence.split((Pattern
									.quote(".")));
							String slavenumber = message[1];
							String username = message[2];
							if (TCPServer.slave1users.contains(username)||
									TCPServer.slave2users.contains(username)||
									TCPServer.slave3users.contains(username)
									|| TCPServer.users.contains(username)) {
								outToClient
										.writeBytes("The name is already taken"+ "\n");
							} else {
								outToClient.writeBytes("user registered"+ "\n");
								if(slavenumber.equals("1"))
								TCPServer.slave1users.add(username);
								if(slavenumber.equals("2"))
									TCPServer.slave2users.add(username);
								if(slavenumber.equals("3"))
									TCPServer.slave3users.add(username);
							}
							}
						else{
							if(clientSentence.equals("LOGOFFALLSERVERS")){
								
								int index=TCPServer.usersockets.indexOf(connectionsocket);
								TCPServer.users.remove(index);
								TCPServer.usersockets.remove(index);
								outToClient.writeBytes("You are now logged off"+"\n");
						
		
						}
							else{
							if((clientSentence.length()>=22)&&(clientSentence.substring(0, 22).equals("LOGOFFALLSERVERSSLAVE."))){
								String []s=clientSentence.split((Pattern.quote(".")));
								String username1=s[1];
								System.out.println(username1);
								TCPServer.slave1users.remove(username1);
								
							
						} else {
							if (clientSentence.length() >= 17
									&& clientSentence.substring(0, 16).equals(
											"FromSlaveMessage")) {
								String[] message = clientSentence
										.split((Pattern.quote(".")));
								String slavenumber = message[1];
								String source = message[2];
								String desitnation = message[3];
								String textmessage = message[4];
								if (TCPServer.users.contains(desitnation)) {
									int index = TCPServer.users
											.indexOf(desitnation);
									Socket socket = TCPServer.usersockets
											.get(index);

									
									
								
										
										Socket to = TCPServer.usersockets
												.get(index);
										DataOutputStream outTo = new DataOutputStream(
												to.getOutputStream());
										outTo.writeBytes("From " + source
												+ ": " + textmessage + "\n");
										outToClient.writeBytes("Done"+"\n");

									}
								else if(TCPServer.slave1users.contains(desitnation)){
									Socket masterSocket = new Socket("localhost", 8081);
									DataOutputStream outTo = new DataOutputStream(masterSocket.getOutputStream());
									outTo.writeBytes("FromMasterMessage."+source+"."+desitnation+"."+textmessage+"\n");
									
								}else if(TCPServer.slave2users.contains(desitnation)){
									Socket masterSocket = new Socket("localhost", 8082);
									DataOutputStream outTo = new DataOutputStream(masterSocket.getOutputStream());
									outTo.writeBytes("FromMasterMessage."+source+"."+desitnation+"."+textmessage+"\n");
								}else if(TCPServer.slave3users.contains(desitnation)){
									Socket masterSocket = new Socket("localhost", 8083);
									DataOutputStream outTo = new DataOutputStream(masterSocket.getOutputStream());
									outTo.writeBytes("FromMasterMessage."+source+"."+desitnation+"."+textmessage+"\n");
								}

								 else {
									outToClient.writeBytes("UserNotFound"+"\n");
								}
							}

							else {

								String source;
								String dest;
								String text;
								String[] message = clientSentence
										.split((Pattern.quote(".*.")));

								source = message[0];
								dest = message[1];
								text = message[2];
								int indexsource = TCPServer.users
										.indexOf(source);
								int indexdest = TCPServer.users.indexOf(dest);
								if (indexsource == -1) {
									outToClient
											.writeBytes("You are not Registered!!"
													+ "\n");
								} else {
									if (indexdest == -1) {
										if(TCPServer.slave1users.contains(dest)){
											Socket slave = new Socket("localhost",8081);
											DataOutputStream outToSlave =  new DataOutputStream(
													slave.getOutputStream());
											outToSlave.writeBytes("FromMasterMessage."+source+"."+dest+"."+text+"\n");
										}
										else if(TCPServer.slave1users.contains(dest)){
											Socket masterSocket = new Socket("localhost", 8081);
											DataOutputStream outTo = new DataOutputStream(masterSocket.getOutputStream());
											outTo.writeBytes("FromMasterMessage."+source+"."+dest+"."+text+"\n");
											
										}else if(TCPServer.slave2users.contains(dest)){
											Socket masterSocket = new Socket("localhost", 8082);
											DataOutputStream outTo = new DataOutputStream(masterSocket.getOutputStream());
											outTo.writeBytes("FromMasterMessage."+source+"."+dest+"."+text+"\n");
											
										}else if(TCPServer.slave3users.contains(dest)){
											Socket masterSocket = new Socket("localhost", 8083);
											DataOutputStream outTo = new DataOutputStream(masterSocket.getOutputStream());
											outTo.writeBytes("FromMasterMessage."+source+"."+dest+"."+text+"\n");
										}
										else{
										outToClient
												.writeBytes("Your Friend isn't Registered"
														+ "\n");}
									} else {
									
											Socket to = TCPServer.usersockets
													.get(indexdest);
											DataOutputStream outTo = new DataOutputStream(
													to.getOutputStream());
											outTo.writeBytes("From " + source
													+ ": " + text + "\n");

										}

									}
								}
							}
						}
						}
					}
					}
			
				
	}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
