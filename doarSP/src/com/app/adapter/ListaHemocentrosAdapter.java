package com.app.adapter;

import java.util.List;

import com.app.doarsp.R;
import com.app.model.Hemocentros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListaHemocentrosAdapter extends ArrayAdapter<Hemocentros> {
	
	private Context context;
	private List<Hemocentros> hemocentros = null;

	public ListaHemocentrosAdapter(Context context,  List<Hemocentros> hemocentros) {
	        super(context,0, hemocentros);
	        this.hemocentros = hemocentros;
	        this.context = context;
	}
	
	public View getView(int position, View view, ViewGroup parent ){
		
		Hemocentros hemocentrosmod = hemocentros.get(position);
	         
	    if(view == null)
	    	view = LayoutInflater.from(context).inflate(R.layout.item_lista_hemocentros, null);
	         
	    TextView textViewNome = (TextView) view.findViewById(R.id.ListaHemocentrosNome);
	    textViewNome.setText(hemocentrosmod.getNomePosto());
	    
	    TextView textViewEnd = (TextView) view.findViewById(R.id.ListaHemocentrosEndereco);
	    textViewEnd.setText(hemocentrosmod.getNomePosto());
	 
	    return view;
	}
}
