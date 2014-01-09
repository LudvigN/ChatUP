package com.killerbees.chatup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ChatFragment extends Fragment
{

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
	    
	    
	    EditText chat_box = (EditText)view.findViewById(R.id.txtChat);
	    Button send = (Button)view.findViewById(R.id.btnSend);
	    final EditText chat_msg = (EditText)view.findViewById(R.id.txtMessage);
	    
	    
	    final Client client = new Client("192.168.0.11", "Feckan", chat_box);
	    client.execute("Tjeena");
	    
	    send.setOnClickListener(new OnClickListener() {
		        
		        @Override
		        public void onClick(View v)
		    	{
		    	   client.sendMessage("#MSG " + String.valueOf(chat_msg.getText()));
		    	   chat_msg.setText("");
		    	}
		    });
	    
	    return view;
	}

  

}
