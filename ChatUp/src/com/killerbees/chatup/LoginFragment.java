package com.killerbees.chatup;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
	    

	    return view;
	}

}