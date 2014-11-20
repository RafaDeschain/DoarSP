package com.app.doarsp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ConsultarRanking extends Fragment  {
	
	public ConsultarRanking(){
		
		
	}
	
	public View OnCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
		ListView listView = (ListView)rootView.findViewById(R.id.lista_ranking);
		
		return rootView;
	}

}
