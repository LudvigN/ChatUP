import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Client extends JFrame {

	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	private String nickName;
	
	public Client(String host, String nick){
		super("Client");
		nickName = nick;
		serverIP = host;
		userText = new JTextField();
		userText.setEnabled(false);
		userText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				if(event.getActionCommand().startsWith("#"))
				{
					if(event.getActionCommand().startsWith("#USERS")){
						requestUsersOnline();
						userText.setText("");
					}
					else if(event.getActionCommand().startsWith("#USER"))
					{
						sendMessage(event.getActionCommand());
						userText.setText("");
					}
					else
					{
						showMessage("Unknown command!");
					}
				}
				else
				{			
					sendMessage("#MSG" + event.getActionCommand());
					userText.setText("");
				}
				
			}
		});
		
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();		
		add(new JScrollPane(chatWindow));
		setSize(300,150);
		setVisible(true);
	}
	
	public void startRunning(){
		try{
			connectToServer();
			
			setupStreams();
			
			requestNickName();
			
			listenForInput();
			
		}catch(EOFException eofException){
			showMessage("\n Client terminated connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}finally{
			closeCrap();
		}
	}

	private void requestUsersOnline() {
		try {
			output.writeObject("#USERS");
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void requestNickName() throws IOException {
		output.writeObject("#NICK" + nickName);
		output.flush();
		
		try {
			String response = (String) input.readObject();
			
			if(response.startsWith("#NICK")){
				showMessage(response.substring(5));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void connectToServer() throws IOException {	
			showMessage("Attempting connection... \n");
			
			connection = new Socket(InetAddress.getByName(serverIP), 9999);
			showMessage("Connected to: " + connection.getInetAddress().getHostName());
			
	}

	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Dude you streams are setup! \n");
	}


	private void listenForInput() throws IOException {
		ableToType(true);
		
		do{
			try{
				message = (String) input.readObject();
				
				if(message.startsWith("#MSG"))
				{
					showMessage("\n" + message);
				}
				else if(message.startsWith("#USERS"))
				{
					String[] users = message.substring(6).split("\\,");
					
					showMessage("\n\n Users online: \n");
					
					for(String user : users){
						showMessage("\n" + user);
					}	
				}
				
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("\n I dont know that object");
			}
			
			
		}while(!message.equals("SERVER - END"));
		
	}


	private void sendMessage(String message) {
		try{
			
			output.writeObject(message);
			output.flush();
					
		}catch(IOException ioException){
			chatWindow.append("\n Dude I cant send that");
		}
	}
	
	private void showMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				chatWindow.append(message);
			}
		});
		
	}
	
	private void ableToType(final boolean b) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				userText.setEnabled(b);
			}
		});
		
	}

	private void closeCrap() {
		showMessage("\n Closing crap down");
		ableToType(false);
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
		
	}




}
