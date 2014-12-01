package com.app.adapter;

import java.util.List;

import com.app.doarsp.R;
import com.app.model.Mural;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListaMuralAdapter extends ArrayAdapter<Mural> {
	
	private Context context;
	private List<Mural> mural = null;

	public ListaMuralAdapter(Context context,  List<Mural> mural2) {
	        super(context,0, mural2);
	        this.mural = mural2;
	        this.context = context;
	}
	
	public View getView(int position, View view, ViewGroup parent ){
		
		Mural muralmod = mural.get(position);
	         
	    if(view == null)
	    	view = LayoutInflater.from(context).inflate(R.layout.item_layout_mural, null);
	 	         
	    TextView nomepaciente = (TextView) view.findViewById(R.id.ListaMuralNome);
	    nomepaciente.setText(muralmod.getNomePaciente());
	    
	    TextView tiposanguineo = (TextView) view.findViewById(R.id.ListaMuralTipo);
	    tiposanguineo.setText("Tipo sanguineo: " + muralmod.getTpSanguineo());
	         
	    TextView idDoacao = (TextView) view.findViewById(R.id.ListaIdDoacoesMural);
	    idDoacao.setText(Integer.toString(muralmod.getCodDoacao()));
	    idDoacao.setVisibility(View.INVISIBLE);
	    
	    TextView comentario = (TextView) view.findViewById(R.id.ListaMuralComentario);
	    comentario.setText(muralmod.getComentario());
	    comentario.setVisibility(View.INVISIBLE);
	 
	    return view;	
	}
}
