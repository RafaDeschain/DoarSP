package com.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.doarsp.R;
import com.app.model.HemocentrosModel;

public class HemocentrosAdapter extends BaseAdapter{
	private Context contexto;
	private List<HemocentrosModel> modelo;
	
	public HemocentrosAdapter(Context context, List<HemocentrosModel> model)
	{
		this.contexto = context;
		this.modelo = model;		
	}
	@Override
	public View getView(int position, View view, ViewGroup vg){
		
		HemocentrosModel hemocentro = modelo.get(position);
		
		if(view == null)
			view = LayoutInflater.from(contexto).inflate(R.layout.item_hemocentro, null);
		
	    ImageView imageView = (ImageView) view.findViewById(R.id.imagemPosto);
	    imageView.setImageResource(R.drawable.ic_postos);
	         
	    TextView nomePosto = (TextView) view.findViewById(R.id.nomePosto);
	    TextView enderecoPosto = (TextView) view.findViewById(R.id.endPosto);
	    
	    nomePosto.setText(hemocentro.getNomePosto());     
	    enderecoPosto.setText(hemocentro.getEndPosto());
	    
	    TextView quantSolicitacoes = (TextView) view.findViewById(R.id.quantSolicitacoes);
	    int qtdSolicitacao = hemocentro.getSolicitacoes().size();
	    String txtQtdSolicitacao;
	    
	    if(qtdSolicitacao == 1){
	    	txtQtdSolicitacao = view.getResources().getString(R.string.quantSolicitacao1);
	    } else if(qtdSolicitacao > 1) {
	    	txtQtdSolicitacao = view.getResources().getString(R.string.quantSolicitacaoN) + " " + qtdSolicitacao;
	    } else {
	    	txtQtdSolicitacao = view.getResources().getString(R.string.quantSolicitacao0);
	    }
	    
	    quantSolicitacoes.setText(txtQtdSolicitacao);
	    
	    return view;		
	}
	@Override
	public int getCount()
	{
		return this.modelo.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public Object getItem(int position) {
		return modelo.get(position);
	}
}
