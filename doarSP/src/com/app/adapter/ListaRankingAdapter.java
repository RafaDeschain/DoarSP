package com.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.doarsp.R;
import com.app.model.Ranking;

public class ListaRankingAdapter extends ArrayAdapter<Ranking> {
	
	private Context context;
	private List<Ranking> ranking = null;

	public ListaRankingAdapter(Context context,  List<Ranking> ranking) {
	        super(context,0, ranking);
	        this.ranking = ranking;
	        this.context = context;
	}
	
	public View getView(int position, View view, ViewGroup parent ){
		
		Ranking rankinglmod = ranking.get(position);
		
	    if(view == null)
	    	view = LayoutInflater.from(context).inflate(R.layout.item_lista_ranking, null);
	    
	    ImageView foto = (ImageView)view.findViewById(R.id.ListaMuralImage);
	    
	    switch (position) {
		case 0:
			foto.setImageResource(R.drawable.ic_gold);
			break;
		case 1:
			foto.setImageResource(R.drawable.ic_silver);
			break;
		case 2:
			foto.setImageResource(R.drawable.ic_brown);
			break;
		default:
			foto.setVisibility(View.INVISIBLE);
			break;
		}
	  
	    TextView textViewNome= (TextView) view.findViewById(R.id.ListaRankingNome);
	    textViewNome.setText(rankinglmod.getNome());
	         
	    TextView textViewQtdDoacao = (TextView)view.findViewById(R.id.ListaQtdDoacao);
	    String textqtd = String.valueOf(rankinglmod.getQtdDoacao());
	    textViewQtdDoacao.setText("Quantidade de doações: " + textqtd);
	 
	    return view;	
	}



}
