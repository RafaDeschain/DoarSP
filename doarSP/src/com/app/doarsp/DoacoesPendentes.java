package com.app.doarsp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.ListaDoacoesAdapter;
import com.app.model.Doacao;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("SimpleDateFormat")
public class DoacoesPendentes extends Fragment implements InterfaceListener{
	
	private int idUser;
	private int idDoacao;
	private int idSolicitacao;
	private int idHemocentro;
	
	private TextView idDoacaoTV, idSolicitacaoTV, idHemocentroTV;
	
	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;
	
	/** Solicitacoes **/
	public List<Doacao> listaSol;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MainActivity global = (MainActivity)getActivity();
		idUser = global.getUser().getCodUsuario();
		
		params = new String[1][2];
		params[0][0] = "userID";
		params[0][1] = String.valueOf(idUser);
		
		webservice = new WebService("solicitacoes_GetDoacao", params);
		thread = new Thread(getActivity(), webservice, getInterface());
		thread.execute();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_lista_doacoes_pendentes, container, false);
		
		return rootView;
	}

	@Override
	public void returningCall(String result) {
		// TODO Auto-generated method stub
		if(result.equalsIgnoreCase("[true]")){
			Configuracao.showDialog(getActivity(), "Checkin efetuado", "Obrigado por fazer a diferença na vida das pessoas :)", true);
			
			MainActivity global = (MainActivity)getActivity();
			global.getUser().setStatusApto(false);
			
			SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
			global.getUser().setDtdUltimaDoacao(format.format(new Date()));
			
			Principal p = new Principal();
			Configuracao.trocarFragment(p, getFragmentManager(), false);
		}
		else if(result.equalsIgnoreCase("[false]")){
			Configuracao.showDialog(getActivity(), "DoarSP", "Não foi possivel realizar o checkin pois sua localização não é proxima do hemocentro", true);
		}
		else if(result.equalsIgnoreCase("Erro")){
			Configuracao.showDialog(getActivity(), "DoarSP", "Não foi possivel obter sua localização, por favor verifique se seu GPS encontra-se ligado", true);
		}
		else{
			preencherDoacoes(result);
		}
	}
	
	/** Preenche as Doações Pendentes **/
	public void preencherDoacoes(String doacoes){
		
		if(doacoes.length() > 3){
		
			try
			{
				listaSol = new ArrayList<Doacao>();
				
				if(doacoes.length() > 3){
					JSONArray jsonarray = new JSONArray(doacoes);
					
					for(int i = 0; i < jsonarray.length(); i++){
						
						JSONObject json = jsonarray.getJSONObject(i);
						
						listaSol.add(criarDoacao(
									json.getInt("idDoacao"),
									json.getInt("idSolicitacao"),
									json.getInt("idHemocentro"),
									json.getString("nomePaciente")
									));
					}
				}
				
				preencheTela(listaSol);
			}
			catch(Exception e){
				e.getMessage();
			}
		}
		else{
			Configuracao.showDialog(getActivity(), "DoarSP", "Você ainda não criou nenhuma doação", true);
			Principal p = new Principal();
			Configuracao.trocarFragment(p, getFragmentManager(), false);
		}
	}
	
	/** Preenche o ListView **/
	private void preencheTela(List<Doacao> listaSol) {
		
		//Solicitações
        
        ListView listView = (ListView)getActivity().findViewById(R.id.BuscarListaDoacoesPendentes);
               
        if(!listaSol.isEmpty()){
            final ListaDoacoesAdapter doaAdapter = new ListaDoacoesAdapter(getActivity(), listaSol);
            listView.setAdapter(doaAdapter);
            listView.setSelector(R.drawable.list_selector_buscar);
        }
        else{
        	listView.setVisibility(View.INVISIBLE);
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
		
		/** Listener **/
		listView.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
    			
    			idDoacaoTV 		= (TextView) view.findViewById(R.id.ListaIdDoacoesPendentes);
    	        idHemocentroTV 	= (TextView) view.findViewById(R.id.ListaIdHemocentroPendentes);
    	        idSolicitacaoTV = (TextView) view.findViewById(R.id.ListaIdSolicitacaoPendentes);
    	        
    	        idDoacao 		= Integer.parseInt((String) idDoacaoTV.getText());
    	        idHemocentro 	= Integer.parseInt((String) idHemocentroTV.getText());
    	        idSolicitacao 	= Integer.parseInt((String) idSolicitacaoTV.getText());
    			
    			view.setSelected(false);
    			view.setPressed(false);
    			view.clearFocus();
    			view.refreshDrawableState();
    			
    			AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
	            builder1.setMessage("Deseja efetuar o checkin para esta doação?");
	            builder1.setCancelable(true);
	            builder1.setPositiveButton("Sim",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                	
	                	MainActivity global = (MainActivity)getActivity();
						
						params = new String[4][2];
						
						params[0][0] = "userId";
						params[0][1] = String.valueOf(global.getUser().getCodUsuario());
						params[1][0] = "idDoacao";
						params[1][1] = String.valueOf(idDoacao);
						params[2][0] = "idSolicitacao";
						params[2][1] = String.valueOf(idSolicitacao);
						params[3][0] = "idHemo";
						params[3][1] = String.valueOf(idHemocentro);
						
						dialog.cancel();
						
						webservice = new WebService("doacao_CheckInDoacao", params);
						thread = new Thread(getActivity(), webservice, getInterface());
						thread.execute();
	                }
	            });
	            builder1.setNegativeButton("Não",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                }
	            });

	            AlertDialog alert11 = builder1.create();
	            alert11.show();
    			
    		}
    	});
	}

	public Doacao criarDoacao(int idDoacao, int idSolicitacao, int idHemocentro, String nomePaciente){
		Doacao doacao = new Doacao(idDoacao, idSolicitacao, idHemocentro, nomePaciente);
		return doacao;
	}
	
	/** Getters and Setters **/
	
	public InterfaceListener getInterface(){
		return this;
	}
	
	/** Getters and Setters End **/

}
