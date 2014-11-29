package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.ListaHemocentrosAdapter;
import com.app.doarsp.Principal.GetSolicitacao;
import com.app.model.Hemocentros;
import com.app.model.Solicitacoes;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FeedDoacoes extends Fragment implements InterfaceListener{
	
	TextView versionInfo;
	private View rootView;
	
	private ListView pstDoacao;
	private int idHemo = -1;
	
	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;
	
	/** Solicitacoes **/
	public List<Solicitacoes> listaSol;
	GetSolicitacao solicitacao;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_feeddoacoes, container, false);
		
		pstDoacao = (ListView)rootView.findViewById(R.id.BuscarListaHemocentros);
		
		List<Hemocentros> hemo = gerarHemocentros();
		
		final ListaHemocentrosAdapter hemoAdapter = new ListaHemocentrosAdapter(getActivity(), hemo);
        pstDoacao.setAdapter(hemoAdapter);
        pstDoacao.setSelector(R.drawable.list_selector_buscar);
        
        /** Listener **/
    	pstDoacao.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
    			
    			TextView id = (TextView) view.findViewById(R.id.ListaIdHemocentro);
    			idHemo = Integer.parseInt((String) id.getText());
    			
    			view.setSelected(false);
    			view.setPressed(false);
    			view.clearFocus();
    			view.refreshDrawableState();
    			
				if(idHemo != -1){
					
					params = new String[1][2];
					params[0][0] = "idHemocentro";
					params[0][1] = (String) id.getText();
					
					webservice = new WebService("hemocentros_getSolicitacoesHemocentro", params);
					thread = new Thread(getActivity(), webservice, getInterface());
					thread.execute();
				}
    			
    		}
    	});
        
        //Método para fazer funcionar o Scroll das solicitações
        pstDoacao.setOnTouchListener(new ListView.OnTouchListener() {
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
		
		return rootView;
	}
	
	/** Returning Call **/
	@Override
	public void returningCall(String solicitacoes) {
		
		try
		{
			listaSol = new ArrayList<Solicitacoes>();
			
			if(solicitacoes.length() > 3){
				JSONArray jsonarray = new JSONArray(solicitacoes);
				
				for(int i = 0; i < jsonarray.length(); i++){
					
					JSONObject json = jsonarray.getJSONObject(i);
					
					listaSol.add(criarSolicitacao(json.getInt("codDoacao"), 
							 json.getInt("idUserSolicitante"), 
							 json.getInt("qtnDoacoes"), 
							 json.getInt("qtnRealizadas"), 
							 json.getInt("hemoCentro"), 
							 json.getInt("tpSanguineo"), 
							 1,
							 json.getString("nomePaciente"), 
							 json.getString("dataAbertura"),
							 json.getString("comentario")));
				}
				
				ListaSolicitacoes sol = new ListaSolicitacoes(listaSol);
				Configuracao.trocarFragment(sol, getFragmentManager(), true);
			}
			else{
				Configuracao.showDialog(getActivity(), "DoarSP", "Não há nenhuma solicitação aberta neste hemocentro", true);
			}
			
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	
	/** Criar solicitações **/
	
	public Solicitacoes criarSolicitacao(int idSolicitacao, int idUsuario,
			int quantidadeSolicitacoes, int quantidadesRealizadas,
			int idHemocentro, int tipoSanguineo, int status, String nome,
			String data, String comentario){
		
		Solicitacoes sol = new Solicitacoes(idSolicitacao, idUsuario, quantidadeSolicitacoes, 
				quantidadesRealizadas, idHemocentro, tipoSanguineo, status, nome, data, comentario);
		
		return sol;
	}
	
	/** Criando a lista de hemocentros **/
	private List<Hemocentros> gerarHemocentros() {
		
		//Instancia o objeto que faz o retorno dos postos.
		Hemocentros postos = new Hemocentros(getActivity().getApplicationContext());
		
		//Query que vai retornar os postos que devem ser iterados nas marcações.
		Cursor query = postos.getAllPostos();
		query.moveToFirst();
		List<Hemocentros> lista = new ArrayList<Hemocentros>();
		
		for (int i = 0; i < query.getCount(); i ++)
		{
			lista.add(criarHemocentro(query.getInt(0), query.getString(2), query.getString(1)));
			query.moveToNext();
		}
		
        return lista;
    }
     
    private Hemocentros criarHemocentro(int id, String nome, String endereco) {
    	Hemocentros hemocentro = new Hemocentros(id, nome, endereco);
        return hemocentro;
    }
    
    /** Fim **/
    
    /** Getters and Setters **/
		
	public InterfaceListener getInterface(){
		return this;
	}
	
	/** Getters and Setters End **/
}