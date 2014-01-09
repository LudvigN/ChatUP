import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class ClientHandler implements Runnable {


	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	
	private OnNewMessageListener listener;
	private OnDisconnectedListener disconnectedListener;
	private String nickName;
	
	public synchronized void setNickName(String name) {
		nickName = name;
		
		sendMessage("#NICK Welcome " + nickName + " !");
	}

	public synchronized String getNickName(){
		return nickName;
	}
	
	public ClientHandler(Socket connection, OnNewMessageListener listener, OnDisconnectedListener dlistener){
		this.connection = connection;
		this.listener = listener;
		this.disconnectedListener = dlistener;
		nickName = "";
	}
		
	
	@Override
	public void run() {
		try {
			setupStreams();			
			listenForInput();
			closeCrap();
		} catch (IOException e) {
			e.printStackTrace();
			closeCrap();
		}	
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		
	}


	
	private void listenForInput() throws IOException {
		String message = "";		
		do{
			try{
				message = (String)input.readObject();

				listener.processInput(message, this);
	
				
			}catch(ClassNotFoundException classNotFoundException){
				classNotFoundException.printStackTrace();
			}
		}while(!message.equals("#END"));
		

	}


	private void closeCrap() {
		listener.processInput("#MSG disconnected!", this);
		
		try{
			output.close();
			input.close();
			connection.close();
			disconnectedListener.disconnect(this);			
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
		
		
	}
	
	
	public synchronized void sendMessage(String message) {
			try{
				output.writeObject(message);
				output.flush();
			}catch(IOException ioException){
				ioException.printStackTrace();
			}
	}
		
	public interface OnNewMessageListener{
		void processInput(String message, ClientHandler client);
	}
	
	public interface OnDisconnectedListener{
		void disconnect(ClientHandler client);
	}

	
}
