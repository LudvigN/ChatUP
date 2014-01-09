package com.killerbees.chatup;

import java.io.ObjectOutputStream.PutField;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.Menu;

public class MainActivity extends Activity
{

   

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    
	    
	   FragmentTransaction ft = getFragmentManager().beginTransaction();
	   ft.replace(R.id.fragment_container, new LoginFragment());
	   ft.commit();  
	    
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
	    // Inflate the menu; this adds items to the action bar if it is
	    // present.
	    getMenuInflater().inflate(R.menu.main, menu);
	    return true;
	}
    
    
    public void login(String userName)
    {
	   Bundle data = new Bundle();
	   data.putString("nickname", userName);
	   ChatFragment chat = new ChatFragment();
	   chat.setArguments(data);
	   FragmentTransaction ft = getFragmentManager().beginTransaction();
	   ft.replace(R.id.fragment_container, chat);
	   ft.commit();  
	   
	   
		   
    }
    
  

}
