package com.killerbees.chatup;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ChatFragment extends Fragment implements OnClickListener
{
    Client client;
    EditText chat_msg;
    EditText chat_box;
    Button send;
      
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
	    // TODO Auto-generated method stub
	    super.onCreate(savedInstanceState);
	}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
	{
	    View view = inflater.inflate(R.layout.chat_fragment, container, false);
	   
	    String nickname = this.getArguments().getString("nickname");
	    chat_box = (EditText)view.findViewById(R.id.txtChat);
	    send = (Button)view.findViewById(R.id.btnSend);
	    chat_msg = (EditText)view.findViewById(R.id.txtMessage);
	  
	    
	    
	    
	    client = new Client("192.168.0.11", nickname, chat_box);
	    client.execute("Tjeena");
	    
	    send.setOnClickListener(this);
	  
	    return view;
	}
    

    @Override
    public void onClick(View v)
	{
	    
	    	   String message = String.valueOf(chat_msg.getText());
	    
	    	   
	    	   if(!message.startsWith("#"))
	    	       message = "#MSG " + message;
	    	   
	    	   client.sendMessage(message);
	    	   chat_msg.setText("");
	    
	}
  
}
