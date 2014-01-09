import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BroadcastHandler implements ClientHandler.OnNewMessageListener, ClientHandler.OnDisconnectedListener {

	ArrayList<ClientHandler> mClients;
	
	public BroadcastHandler(){
		mClients = new ArrayList<ClientHandler>();
	} 
	
	public void addClient(Socket connection){
		
		ClientHandler client = new ClientHandler(connection, this, this);
		Thread t = new Thread(client);
		t.start();
		
		mClients.add(client);
	}
	


	@Override
	public void processInput(String message, ClientHandler client) {
		
		if(message.startsWith("#NICK"))
		{
			client.setNickName(message.substring(5));
		}
		else if(message.startsWith("#MSG"))
		{
			if(client.getNickName() != "")
			{
				for(ClientHandler mClient : mClients)
				{
					mClient.sendMessage("#MSG" + client.getNickName() + " - " + message.substring(4));
				}
			}
		}
		else if(message.startsWith("#USERS"))
		{
			StringBuilder sb = new StringBuilder("#USERS");
			
			sb.append(" ");
			
			for(ClientHandler mClient : mClients){
				if(!mClient.equals(client)){
					 sb.append(mClient.getNickName() + ",");
				}
			}
			
			client.sendMessage(sb.toString());
			
		}
		else if(message.startsWith("#USER")){
			String temp = message.substring(6).trim();
			String nickname = "";
						
			Pattern regex = Pattern.compile("^([\\w\\-]+)");
			Matcher m = regex.matcher(temp);
			
			while(m.find()){
				nickname = m.group();	
			}
			
			temp = temp.replaceAll("^([\\w\\-]+)", "").trim();
			
			boolean nickFound = false;
			
			for(ClientHandler mClient : mClients)
			{			
				if(mClient.getNickName().equals(nickname) || mClient.equals(client))
				{
					nickFound = true;
					mClient.sendMessage("#MSG" + client.getNickName() + " - " + temp);
				}
			}
			
			if(!nickFound)
			{
				client.sendMessage("#MSG User not found!");
			}
			
		}
		
		
	}

	@Override
	public void disconnect(ClientHandler client) {
		
		mClients.remove(client);
		
		
		
	}
	
}
