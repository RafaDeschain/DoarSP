package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.app.doarsp.Utils;

import com.app.adapter.ListaMuralAdapter;
import com.app.doarsp.R;

import com.app.model.MuralModel;

public class Mural extends Fragment {
	

	public Mural() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_mural,
				container, false);
		
		ListView listView = (ListView)rootView.findViewById(R.id.lista_doacoes);
		
		List<MuralModel> mural = gerarDoacaoMSG(); // metodo para criar a lista (dados).
		
	     
		final ListaMuralAdapter muralAdapter = new ListaMuralAdapter(getActivity(), mural);
		listView.setAdapter(muralAdapter);
		 
		listView.setOnItemClickListener(new OnItemClickListener(){
			
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
				
				MuralModel mural = muralAdapter.getItem(position);
				Utils.trocarFragment(new MensagemDoMural(), getFragmentManager(), true);
			    //Toast.makeText(getActivity(),mural.getComentario(), Toast.LENGTH_SHORT).show();    
					
			}
		});
				
	
		return rootView;
	}
	
	public List<MuralModel> gerarDoacaoMSG() {
	    
		List<MuralModel> mural = new ArrayList<MuralModel>();
	    mural.add(criarMural(1,1,"teste1",R.drawable.ic_firstachivement));
	    mural.add(criarMural(2,2,"teste2",R.drawable.ic_firstachivement));
	    mural.add(criarMural(2,2,"teste3",R.drawable.ic_firstachivement ));
	     
	    return mural;
	}
	
	private MuralModel criarMural(int CodMural,int CodSolicitacao, String Comentario, int imagem){
		
		MuralModel mural = new MuralModel(CodMural,CodSolicitacao,Comentario, imagem);
		
		return mural;
	}


}