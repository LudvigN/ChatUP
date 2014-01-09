package com.killerbees.chatup;

import java.io.*;
import java.net.*;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

public class Client extends AsyncTask<String, String, String> {





	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	private String nickName;
	private EditText chat_box;
	
	public Client(String host, String nick, EditText chat_box){
	
		nickName = nick;
		serverIP = host;
		this.chat_box = chat_box;
		
	}
	
	public void startRunning(){
		try{
			connectToServer();
			
			setupStreams();
			
			requestNickName();
			
			listenForInput();
			
		}catch(EOFException eofException){
		    Log.d("eofException", "EOF");
		    publishProgress("\n Client terminated connection");
		}catch(IOException ioException){
		    Log.d("IOException", "Detta exceptionet var kallat från startRunning metoden" );
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
		output.writeObject("#NICK " + nickName);
		output.flush();
		
		try {
			String response = (String) input.readObject();
			
			if(response.startsWith("#NICK")){
			    publishProgress(response.substring(5));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	    protected String doInBackground(String... params)
		{
		    startRunning();
		    
		    return "\n Server terminated the connnection";
		}
	
	
	@Override
	    protected void onProgressUpdate(String... values)
		{
		    chat_box.append(values[0]);
		    
		    super.onProgressUpdate(values);
		}

	    @Override
	    protected void onPostExecute(String result)
		{
		    
		    chat_box.append(result);
		    
		    super.onPostExecute(result);
		}

	private void connectToServer() throws IOException {	
			publishProgress("Attempting connection... \n");
			
			connection = new Socket(InetAddress.getByName(serverIP), 9999);
			publishProgress("Connected to: " + connection.getInetAddress().getHostName());
			
	}

	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		publishProgress("\n Dude you streams are setup! \n");
	}


	private void listenForInput() throws IOException {		
		do{
			try{
				message = (String) input.readObject();
				
				if(message.startsWith("#MSG"))
				{
				    
				    publishProgress("\n" + message.substring(5));
				}
				else if(message.startsWith("#USERS"))
				{
					String[] users = message.substring(6).split("\\,");
					
					publishProgress("\n\n Users online: \n");
					
					for(String user : users){
					    publishProgress("\n" + user);
					}	
				}
				
			}catch(ClassNotFoundException classNotFoundException){
			    publishProgress("\n I dont know that object");
			}
			
			
		}while(!message.equals("SERVER - END"));
		
	}


	public synchronized void sendMessage(String message) {
		try{
			
			output.writeObject(message);
			output.flush();
					
		}catch(IOException ioException){
		    publishProgress("\n Dude I cant send that");
		}
	}

	
	

	private void closeCrap() {
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
		
	}




}
