import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import java.awt.Font;

public class Server extends JFrame {

	public static final int MAX_CLIENT = 100;
	public static final int PORT = 2300;
	public int currentGame;
	ServerSocket serverSocket;

	JLabel serveInfoLabel = new JLabel("Server is OFF", JLabel.CENTER);
	JButton startServerButton;
	JLabel numOfCurrentClientLabel;
	JLabel numOfCurrentGameLabel;

	/* constructor */
	public Server() {
		currentGame = 0;
		initFrame();
	}

	/* Initialize the contents of the frame */
	private void initFrame() {

		setTitle("Scrabble server");
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(500, 300));

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(15, 15, 15, 15);

		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;

		add(serveInfoLabel, c);
		// c.gridy++;
		// numOfCurrentClientLabel = new JLabel("Number of clients in queue: " +
		// clientSocketQ.size());
		// add(numOfCurrentClientLabel, c);
		// c.gridy++;
		// numOfCurrentGameLabel = new JLabel("Number of games running: " +
		// currentGame);
		// add(numOfCurrentGameLabel, c);
		c.gridy++;
		startServerButton = new JButton("Start Server");
		add(startServerButton, c);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					startServer();
					startServerButton.setEnabled(false);
					serveInfoLabel.setText("Server is ON");
					System.out.println("Server started");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server server = new Server();
					server.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Start the Server
	 */
	public void startServer() throws Exception {
		System.out.println("Server is starting...");
		serverSocket = new ServerSocket(PORT);
		Thread t = new connector(serverSocket);
		t.start();
	}
}

class Game extends Thread {

	private Socket s1, s2, s3, s4;
	String msg;

	public Game(Socket s1, Socket s2, Socket s3, Socket s4) {
		this.s1 = s1;
		this.s2 = s2;
		this.s3 = s3;
		this.s4 = s4;
	}

	void sendMsg(Socket s, String msg) throws IOException {
		DataOutputStream out = new DataOutputStream(s.getOutputStream());
		out.writeUTF(msg);
		// System.out.print("\nEnter your message: ");
	}

	String receiveMsg(Socket s) throws IOException {
		DataInputStream in = new DataInputStream(s.getInputStream());
		String msg = in.readUTF();
		//System.out.println("\nFrom client: " + msg);
		// System.out.print("\nEnter your message: ");
		return msg;
	}

	public void run() {
		try {
			sendMsg(s1, "ok1");
			sendMsg(s2, "ok2");
			sendMsg(s3, "ok3");
			sendMsg(s4, "ok4");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		boolean flag;
		String s;
		int passCount=0;
		
		while (true) {
			try {
				// game running
//				flag = true;
//				while(flag) {
//					msg = receiveMsg(s1);
//					s = gs.move(msg);
//					if(s.charAt(0)>='0' && s.charAt(0)<='9') {
//						sendMsg(s1, "1 "+s);
//						
//						sendMsg(s1, "1 "+s);
//						sendMsg(s1, msg);
//						sendMsg(s2, "1 "+s);
//						sendMsg(s2, msg);
//						sendMsg(s3, "1 "+s);
//						sendMsg(s3, msg);
//						sendMsg(s4, "1 "+s);
//						sendMsg(s4, msg);
//						
//						flag = false;
//					}
//					else {
//						sendMsg(s1, s);
//					}
//				}
//				
//				flag = true;
//				while(flag) {
//					msg = receiveMsg(s2);
//					s = gs.move(msg);
//					if(s.charAt(0)>='0' && s.charAt(0)<='9') {
//						sendMsg(s2, "2 "+s);
//						
//						sendMsg(s1, "2 "+s);
//						sendMsg(s1, msg);
//						sendMsg(s2, "2 "+s);
//						sendMsg(s2, msg);
//						sendMsg(s3, "2 "+s);
//						sendMsg(s3, msg);
//						sendMsg(s4, "2 "+s);
//						sendMsg(s4, msg);
//						
//						flag = false;
//					}
//					else {
//						sendMsg(s2, s);
//					}
//				}
//				
//				flag = true;
//				while(flag) {
//					msg = receiveMsg(s3);
//					s = gs.move(msg);
//					if(s.charAt(0)>='0' && s.charAt(0)<='9') {
//						sendMsg(s3, "3 "+s);
//						
//						sendMsg(s1, "3 "+s);
//						sendMsg(s1, msg);
//						sendMsg(s2, "3 "+s);
//						sendMsg(s2, msg);
//						sendMsg(s3, "3 "+s);
//						sendMsg(s3, msg);
//						sendMsg(s4, "3 "+s);
//						sendMsg(s4, msg);
//						
//						flag = false;
//					}
//					else {
//						sendMsg(s3, s);
//					}
//				}
//				
//				flag = true;
//				while(flag) {
//					msg = receiveMsg(s4);
//					s = gs.move(msg);
//					if(s.charAt(0)>='0' && s.charAt(0)<='9') {
//						sendMsg(s4, "4 "+s);
//						
//						sendMsg(s1, "4 "+s);
//						sendMsg(s1, msg);
//						sendMsg(s2, "4 "+s);
//						sendMsg(s2, msg);
//						sendMsg(s3, "4 "+s);
//						sendMsg(s3, msg);
//						sendMsg(s4, "4 "+s);
//						sendMsg(s4, msg);
//						
//						flag = false;
//					}
//					else {
//						sendMsg(s4, s);
//					}
//				}
				
				
				
				
				msg = receiveMsg(s1);
				if(msg.charAt(0) == 'p') passCount++;
				else passCount=0;
				if(passCount >=4) {
					sendMsg(s1, "exit");
					sendMsg(s2, "exit");
					sendMsg(s3, "exit");
					sendMsg(s4, "exit");
					sleep(2500);
					System.exit(0);
				}
				sendMsg(s2, msg);
				sendMsg(s3, msg);
				sendMsg(s4, msg);
				msg = receiveMsg(s1);
				sendMsg(s2, msg);
				sendMsg(s3, msg);
				sendMsg(s4, msg);
				
				msg = receiveMsg(s2);
				if(msg.charAt(0) == 'p') passCount++;
				else passCount=0;
				if(passCount >=4) {
					sendMsg(s1, "exit");
					sendMsg(s2, "exit");
					sendMsg(s3, "exit");
					sendMsg(s4, "exit");
					sleep(2500);
					System.exit(0);
				}
				sendMsg(s1, msg);
				sendMsg(s3, msg);
				sendMsg(s4, msg);
				msg = receiveMsg(s2);
				sendMsg(s1, msg);
				sendMsg(s3, msg);
				sendMsg(s4, msg);
				
				msg = receiveMsg(s3);
				if(msg.charAt(0) == 'p') passCount++;
				else passCount=0;
				if(passCount >=4) {
					sendMsg(s1, "exit");
					sendMsg(s2, "exit");
					sendMsg(s3, "exit");
					sendMsg(s4, "exit");
					sleep(2500);
					System.exit(0);
				}
				sendMsg(s1, msg);
				sendMsg(s2, msg);
				sendMsg(s4, msg);
				msg = receiveMsg(s3);
				sendMsg(s1, msg);
				sendMsg(s2, msg);
				sendMsg(s4, msg);
				
				
				msg = receiveMsg(s4);
				if(msg.charAt(0) == 'p') passCount++;
				else passCount=0;
				if(passCount >=4) {
					sendMsg(s1, "exit");
					sendMsg(s2, "exit");
					sendMsg(s3, "exit");
					sendMsg(s4, "exit");
					sleep(2500);
					System.exit(0);
				}
				sendMsg(s1, msg);
				sendMsg(s2, msg);
				sendMsg(s3, msg);
				msg = receiveMsg(s4);
				sendMsg(s1, msg);
				sendMsg(s2, msg);
				sendMsg(s3, msg);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

class connector extends Thread {
	private ServerSocket serverSocket;
	Queue<Socket> clientSocketQ;

	public connector(ServerSocket serversocket) {
		serverSocket = serversocket;
		clientSocketQ = new LinkedList<Socket>();
	}

	public void run() {
		while (true) {
			try {
				Socket sc = serverSocket.accept();
				System.out.println("Just connected to "
						+ sc.getRemoteSocketAddress());

				clientSocketQ.add(sc);

				if (clientSocketQ.size() >= 4) {
					Socket s1 = clientSocketQ.remove();
					Socket s2 = clientSocketQ.remove();
					Socket s3 = clientSocketQ.remove();
					Socket s4 = clientSocketQ.remove();
					Thread t = new Game(s1, s2, s3, s4);
					t.start();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
