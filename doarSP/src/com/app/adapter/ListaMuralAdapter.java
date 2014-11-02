package com.app.adapter;

import java.util.List;

import com.app.doarsp.R;
import com.app.model.Mural;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaMuralAdapter extends ArrayAdapter<Mural> {
	
	private Context context;
	private List<Mural> mural = null;

	public ListaMuralAdapter(Context context,  List<Mural> mural) {
	        super(context,0, mural);
	        this.mural = mural;
	        this.context = context;
	}
	
	public View getView(int position, View view, ViewGroup parent ){
		
		Mural muralmod = mural.get(position);
	         
	    if(view == null)
	    	view = LayoutInflater.from(context).inflate(R.layout.item_layout_mural, null);
	 
	    ImageView imageViewMural = (ImageView) view.findViewById(R.id.image_view_mural);
	    imageViewMural.setImageResource(muralmod.getImagem());
	         
	    TextView textViewNomeComentario = (TextView) view.findViewById(R.id.text_view_nome_Comentario);
	    textViewNomeComentario.setText(muralmod.getComentario());
	         
	    TextView textViewId = (TextView)view.findViewById(R.id.text_view_id);
	    String textoId = String.valueOf(muralmod.getCodSolicitacao());
	    textViewId.setText(textoId);
	 
	    return view;	
	}
}
