package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.adapter.ListaMuralAdapter;
import com.app.doarsp.R;

import com.app.model.Mural;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

public class ListaMural extends Fragment implements InterfaceListener{
	
	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;
	
	/** Solicitacoes **/
	public List<Mural> listaMural;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_mural, container, false);
		
		MainActivity global = (MainActivity)getActivity();
        
		params = new String[1][2];
		params[0][0] = "userID";
		params[0][1] = String.valueOf(global.getUser().getCodUsuario());
		
		webservice = new WebService("mural_PegarComentario", params);
		thread = new Thread(getActivity(), webservice, getInterface());
		
		thread.execute();
		
		return rootView;
	}
	
	@Override
	public void returningCall(String result) {
		
		try
		{
			if(result.length() > 3){
				
				listaMural = new ArrayList<Mural>();
				JSONArray jsonarray = new JSONArray(result);
				
				for(int i = 0; i < jsonarray.length(); i++){
					
					JSONObject json = jsonarray.getJSONObject(i);
					
					listaMural.add(criarMural(
									json.getInt("codDoacao"),
									json.getInt("tpSanguineo"),
									json.getString("nomePaciente"),
									json.getString("comentario")
									));
				}
			}
			
			preencheTela(listaMural);
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	
	private Mural criarMural(int codDoacao, int tpSanguineo, String nomePaciente,
			String comentario){
		
		Mural mural = new Mural(codDoacao, tpSanguineo, nomePaciente, comentario);
		return mural;
	}
	
	private void preencheTela(List<Mural> listaMural) {
		
		//Solicitações
        
        ListView listView = (ListView)getActivity().findViewById(R.id.lista_doacoes);
        
        if(!listaMural.isEmpty()){
            
        	final ListaMuralAdapter muralAdapter = new ListaMuralAdapter(getActivity(), listaMural);
            listView.setAdapter(muralAdapter);
            listView.setSelector(R.drawable.list_selector_buscar);
            
            /** Listener **/
            listView.setOnItemClickListener(new OnItemClickListener(){
        		public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
        			
        			TextView nomepaciente 	= (TextView) view.findViewById(R.id.ListaMuralNome);
        			TextView comentario 	= (TextView) view.findViewById(R.id.ListaMuralComentario);
        			
        			view.setSelected(false);
        			view.setPressed(false);
        			view.clearFocus();
        			view.refreshDrawableState();
        			
        			Configuracao.showDialog(getActivity(), "Mensagem de: " + nomepaciente.getText(), comentario.getText(), true);
        			
        		}
        	});
        }
        else{
        	listView.setVisibility(View.INVISIBLE);
        	Configuracao.showDialog(getActivity(), "DoarSP", "Você ainda não efetuou nenhuma doação", true);
        	Principal p = new Principal();
        	Configuracao.trocarFragment(p, getFragmentManager(), false);
        }
		
		//Método para fazer funcionar o Scroll das solicitações
		listView.setOnTouchListener(new ListView.OnTouchListener() {
		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            int action = event.getAction();
		            switch (action) {
		            case MotionEvent.ACTION_DOWN:
		                // Disallow ScrollView to intercept touch events.
		                v.getParent().requestDisallowInterceptTouchEvent(true);
		                break;

		            case MotionEvent.ACTION_UP:
		                // Allow ScrollView to intercept touch events.
		                v.getParent().requestDisallowInterceptTouchEvent(false);
		                break;
		            }

		            // Handle ListView touch events.
		            v.onTouchEvent(event);
		            return true;
		        }
		    });
	}
	
	/** Getters and Setters **/
	
	public InterfaceListener getInterface(){
		return this;
	}
	
	/** Getters and Setters End **/


}