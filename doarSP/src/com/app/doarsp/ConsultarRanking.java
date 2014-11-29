package com.app.doarsp;


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
	
	public ConsultarRanking(){
		
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
		ListView listView = (ListView)rootView.findViewById(R.id.lista_ranking);
		setWebservice(new WebService("usuario_GetRanking"));
		thread = new Thread(getActivity(), getWebservice(), getInterface());
		thread.execute();
		
		
		
		final ListaRankingAdapter rankingAdapter = new ListaRankingAdapter(getActivity(),rank);
		listView.setAdapter(rankingAdapter);

		return rootView;
	}
	
	
	
	@Override
	public void returningCall(String result) {
		
		
	   
	    try {
	    	JSONArray json = new JSONArray(result);
		    for(int i=0; i< json.length();  i++)
	    		{
		    		JSONObject userObject = json.getJSONObject(i);
		    		ranking = new Ranking();
	    			ranking.setNome(userObject.getString("nome"));
	    			ranking.setQtdDoacao(userObject.getInt("numDoacoes"));
	    			rank.add(ranking);
	    		}
	    } catch (JSONException e) {
	        e.printStackTrace();
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
