package com.app.doarsp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MensagemDoMural extends Fragment  {
	
	
	public MensagemDoMural(){
		
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_agradecimento_mural,
				container, false);
		
		return rootView;
		
	}

}
