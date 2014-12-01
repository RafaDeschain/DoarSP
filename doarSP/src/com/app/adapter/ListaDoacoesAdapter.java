package com.app.adapter;

import java.util.List;

import com.app.doarsp.R;
import com.app.model.Doacao;
import com.app.model.Hemocentros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListaDoacoesAdapter extends ArrayAdapter<Doacao> {
	
	private Context context;
	private List<Doacao> doacoes = null;

	public ListaDoacoesAdapter(Context context, List<Doacao> doacoes) {
	        super(context,0, doacoes);
	        this.doacoes = doacoes;
	        this.context = context;
	}
	
	public View getView(int position, View view, ViewGroup parent ){
		
		Doacao doacaomod = doacoes.get(position);
	    Hemocentros hemo = new Hemocentros(getContext());
		
	    if(view == null)
	    	view = LayoutInflater.from(context).inflate(R.layout.item_lista_doacoes_pendentes, null);
	    
	    TextView textViewID = (TextView) view.findViewById(R.id.ListaIdDoacoesPendentes);
	    textViewID.setText(Integer.toString(doacaomod.getIdDoacao()));
	    textViewID.setVisibility(View.INVISIBLE);
	    
	    TextView textViewIDSol = (TextView) view.findViewById(R.id.ListaIdSolicitacaoPendentes);
	    textViewIDSol.setText(Integer.toString(doacaomod.getIdSolicitacao()));
	    textViewIDSol.setVisibility(View.INVISIBLE);
	    
	    TextView textViewIDHemo = (TextView) view.findViewById(R.id.ListaIdHemocentroPendentes);
	    textViewIDHemo.setText(Integer.toString(doacaomod.getIdHemocentro()));
	    textViewIDHemo.setVisibility(View.INVISIBLE);
	    
	    TextView textViewNome = (TextView) view.findViewById(R.id.ListaDoacoesPendentesNome);
	    textViewNome.setText(doacaomod.getNomePaciente());
	    
	    TextView textViewEnd = (TextView) view.findViewById(R.id.ListaDoacoesPendentesNomePosto);
	    textViewEnd.setText(hemo.getPosto(doacaomod.getIdHemocentro()).getNomePosto());
	    	 
	    return view;
	}
}
