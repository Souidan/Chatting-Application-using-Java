package packageOne;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RunnableServerFour implements Runnable {
	Socket connectionsocket;
	Socket masterConnectionSockcet;

	public RunnableServerFour(Socket s) throws UnknownHostException, IOException {
		connectionsocket = s;
		masterConnectionSockcet = new Socket("localhost", 6000);

	}

	public void run() {
		String clientSentence;
		try {

			while (true) {

				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionsocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(
						connectionsocket.getOutputStream());
				BufferedReader inFromMaster = new BufferedReader(
						new InputStreamReader(
								masterConnectionSockcet.getInputStream()));
				DataOutputStream outToMaster = new DataOutputStream(
						masterConnectionSockcet.getOutputStream());
				clientSentence = inFromClient.readLine();

				if (clientSentence.length() >= 11
						&& clientSentence.substring(0, 11)
								.equals("joinRequest")) {
					System.out.println("test1");
					outToMaster.writeBytes("FromSlaveRequest.3."
							+ clientSentence.substring(12,
									clientSentence.length()) + "\n");
					String infromM = inFromMaster.readLine();
					
					if (infromM.equals("user registered")) {
						TCPServer4.users.add(clientSentence.substring(12,
								clientSentence.length()));
						System.out.println(TCPServer4.users.get(0));
						TCPServer4.usersockets.add(connectionsocket);
						outToClient
								.writeBytes("You can now chat with other members"
										+ "\n");
						System.out.println("test2");
					} else {
						outToClient.writeBytes("Name is already taken" + "\n");
					}

				} else {
					if (clientSentence.equals("Server/GetList")) {
						outToMaster.writeBytes("Server/GetList" + "\n");
						String listFromServer = inFromMaster.readLine();
						System.out.println(listFromServer);
						outToClient.writeBytes(listFromServer + "\n");
						System.out.println(listFromServer);

					}

					else {
						if (clientSentence.equals("LOGOFFALLSERVERS")) {
							int index = TCPServer4.usersockets
									.indexOf(connectionsocket);
							String name = TCPServer4.users.get(index);
							outToMaster.writeBytes("LOGOFFALLSERVERSSLAVE."
									+ name + "\n");
							TCPServer4.users.remove(index);
							TCPServer4.usersockets.remove(index);
							outToClient.writeBytes("You are now logged off"
									+ "\n");

						}

						else {
							if (clientSentence.length() >= 17
									&& clientSentence.substring(0, 17).equals(
											"FromMasterMessage")) {
								String source;
								String dest;
								String text;
								String[] message = clientSentence.split(Pattern
										.quote("."));

								source = message[1];
								dest = message[2];
								text = message[3];

								int indexdest = TCPServer4.users.indexOf(dest);
								Socket socketdest = TCPServer4.usersockets
										.get(indexdest);

								DataOutputStream outTo = new DataOutputStream(
										socketdest.getOutputStream());
								outTo.writeBytes("From " + source + ": " + text
										+ "\n");

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
								int indexsource = TCPServer4.users
										.indexOf(source);
								int indexdest = TCPServer4.users.indexOf(dest);
								if (indexdest == -1) {
									outToMaster
											.writeBytes("FromSlaveMessage.3."
													+ source + "." + dest + "."
													+ text + "\n");
									if (inFromMaster.readLine().equals(
											"UserNotFound")) {
										outToClient.writeBytes("User not found"
												+ "\n");
									}

								} else {
									Socket to = TCPServer4.usersockets
											.get(indexdest);
									DataOutputStream outTo = new DataOutputStream(
											to.getOutputStream());
									outTo.writeBytes("From " + source + ": "
											+ text + "\n");
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
