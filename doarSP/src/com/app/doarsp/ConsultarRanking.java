package com.app.doarsp;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.app.adapter.ListaRankingAdapter;
import com.app.model.Ranking;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ConsultarRanking extends Fragment implements InterfaceListener  {
	
	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	public Ranking ranking;
	public List<Ranking> rank;
	ListView listView;
	private String[][] wsparams;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		wsparams = new String[1][2];
		
		wsparams[0][0] = "ok";
		wsparams[0][1] = String.valueOf(1);
		
		setWebservice(new WebService("usuario_GetRanking", wsparams));
		thread = new Thread(getActivity(), getWebservice(), getInterface());
		thread.execute();
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
		listView = (ListView)rootView.findViewById(R.id.lista_ranking);
		listView.setSelector(R.drawable.list_selector_buscar);
		
		return rootView;
	}
	
	@Override
	public void returningCall(String result) {
		
	    if(result.length() > 2){
	    	
	    	rank = new ArrayList<Ranking>();
	    	
	    	try {
	    		JSONArray jsonarray = new JSONArray(result);
			    
	    		for(int i = 0; i < jsonarray.length(); i++){
	    			
	    				JSONObject json = jsonarray.getJSONObject(i);
			    		ranking = new Ranking();
		    			ranking.setNome(json.getString("nome"));
		    			ranking.setQtdDoacao(json.getInt("numDoacoes"));
		    			rank.add(ranking);
		    		}
			    
			    if (rank != null){
			    	final ListaRankingAdapter rankingAdapter = new ListaRankingAdapter(getActivity(),rank);
					listView.setAdapter(rankingAdapter);
			    }
			    else{
			    	Configuracao.showDialog(getActivity(), "DoarSP", "Ainda não há nenhuma doação :(", true);
			    	Principal p = new Principal();
			    	Configuracao.trocarFragment(p, getFragmentManager(), false);
			    }
				
		    } catch (JSONException e) {
		    	Configuracao.showDialog(getActivity(), "DoarSP", "Ainda não há nenhuma doação :(", true);
		    	Principal p = new Principal();
		    	Configuracao.trocarFragment(p, getFragmentManager(), false);
		        e.printStackTrace();
		    }
	    }
	}
	
/** Getters and Setters **/
	
	public WebService getWebservice() {
		return webservice;
	}

	public void setWebservice(WebService webservice) {
		this.webservice = webservice;
	}
	
	public InterfaceListener getInterface(){
		return this;
	}

}
