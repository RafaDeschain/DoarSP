package com.app.doarsp;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.adapter.ListaSolicitacoesAdapter;
import com.app.model.Solicitacoes;

@SuppressLint("ValidFragment")
public class ListaSolicitacoes extends Fragment{
	
	List<Solicitacoes> listaSol;
	private View rootView;
	private ListView listView;
	
	public ListaSolicitacoes(List<Solicitacoes> listaSol){
		this.listaSol = listaSol;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView 	= inflater.inflate(R.layout.fragment_buscar_lista, container, false);
		listView 	= (ListView)rootView.findViewById(R.id.BuscarListaSolicitacoes);
		
		final ListaSolicitacoesAdapter hemoAdapter = new ListaSolicitacoesAdapter(getActivity(), listaSol);
        listView.setAdapter(hemoAdapter);
        listView.setSelector(R.drawable.list_selector_hemocentros);
		
		return rootView;
		
	}
}
