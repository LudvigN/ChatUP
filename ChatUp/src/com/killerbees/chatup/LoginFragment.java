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

public class LoginFragment extends Fragment
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
	    View view = inflater.inflate(R.layout.login_fragment, container, false);
	    
	    final EditText nickname = (EditText)view.findViewById(R.id.txtUserName);
	    final Button set = (Button)view.findViewById(R.id.btnLogIn);
	    set.setOnClickListener(new OnClickListener() {
	        
	        @Override
	        public void onClick(View v)
	    	{
	        	Log.i("NickName: ", set.getText().toString());
	        	((MainActivity)getActivity()).login(nickname.getText().toString());
	        	
	    	    
	    	}
	    });
	    

	    return view;
	}

}
