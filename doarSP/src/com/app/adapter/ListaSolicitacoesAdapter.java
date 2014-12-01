package com.app.adapter;

import java.util.List;

import com.app.doarsp.R;
import com.app.model.Hemocentros;
import com.app.model.Solicitacoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListaSolicitacoesAdapter extends ArrayAdapter<Solicitacoes> {
	
	private Context context;
	private List<Solicitacoes> solicitacoes = null;

	public ListaSolicitacoesAdapter(Context context, List<Solicitacoes> solicitacoes) {
	        super(context,0, solicitacoes);
	        this.solicitacoes = solicitacoes;
	        this.context = context;
	}
	
	public View getView(int position, View view, ViewGroup parent ){
		
		Solicitacoes solicitacoesmod = solicitacoes.get(position);
	    Hemocentros hemo = new Hemocentros(getContext());
		
	    if(view == null)
	    	view = LayoutInflater.from(context).inflate(R.layout.item_lista_solicitacoes, null);
	    
	    TextView textViewID = (TextView) view.findViewById(R.id.ListaIdSolicitacao);
	    textViewID.setText(Integer.toString(solicitacoesmod.getIdSolicitacao()));
	    textViewID.setVisibility(View.INVISIBLE);
	    
	    TextView textViewNome = (TextView) view.findViewById(R.id.ListaSolicitacoesNome);
	    textViewNome.setText(solicitacoesmod.getNome());
	    
	    TextView textViewEnd = (TextView) view.findViewById(R.id.ListaSolicitacoesNomePosto);
	    textViewEnd.setText(hemo.getPosto(solicitacoesmod.getIdHemocentro()).getNomePosto());
	    
	    TextView textViewQtd = (TextView) view.findViewById(R.id.ListaSolicitacoesQuantidade);
	    textViewQtd.setText("Doações realizadas: " + solicitacoesmod.getQuantidadesRealizadas());
	 
	    return view;
	}
}
