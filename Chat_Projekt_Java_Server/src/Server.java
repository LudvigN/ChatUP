import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server {

	private ServerSocket server;
	private Socket connection;
	
	public Server(){
	
	}
	
	public void startRunning(){
		
		try{
			server = new ServerSocket(9999, 100);			
			
			BroadcastHandler broadcast = new BroadcastHandler();
			
			while(true){
				try{
					waitForConnection();
										
					broadcast.addClient(connection);
					
				}catch(EOFException eofException){
					showMessage("\n Server ended connection!");
				}finally{
					
				}
				
			}
			
		}catch(IOException ioException){
			ioException.printStackTrace();			
		}
		
	}
	
	private void waitForConnection() throws IOException {
		showMessage("Waiting for someone to connect... \n");
		
				
		connection = server.accept();
		
		showMessage(" Now connected to " + connection.getInetAddress().getHostAddress());
	}
	
	public void showMessage(final String message) {
		System.out.println(message);
	}
	
	
}
