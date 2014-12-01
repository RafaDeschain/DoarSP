package com.app.doarsp;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.app.adapter.ListaSolicitacoesAdapter;
import com.app.model.Solicitacoes;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

@SuppressLint("ValidFragment")
public class ListaSolicitacoes extends Fragment implements InterfaceListener{
	
	List<Solicitacoes> listaSol;
	private View rootView;
	private ListView listView;
	private int idSol = -1;
	private int idUsr;
	
	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;
	
	
	public ListaSolicitacoes(List<Solicitacoes> listaSol){
		this.listaSol = listaSol;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView 	= inflater.inflate(R.layout.fragment_buscar_lista, container, false);
		listView 	= (ListView)rootView.findViewById(R.id.BuscarListaSolicitacoes);
		
		final ListaSolicitacoesAdapter hemoAdapter = new ListaSolicitacoesAdapter(getActivity(), listaSol);
        listView.setAdapter(hemoAdapter);
        listView.setSelector(R.drawable.list_selector_buscar);
        
        MainActivity global = (MainActivity)getActivity();
        idUsr = global.getUser().getCodUsuario();
        
        /** Listener **/
        listView.setOnItemClickListener(new OnItemClickListener(){
    		public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
    			
    			TextView id = (TextView) view.findViewById(R.id.ListaIdSolicitacao);
    			idSol = Integer.parseInt((String) id.getText());
    			
    			view.setSelected(false);
    			view.setPressed(false);
    			view.clearFocus();
    			view.refreshDrawableState();
    			
    			AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
	            builder1.setMessage("Deseja efetuar essa doação?");
	            builder1.setCancelable(true);
	            builder1.setPositiveButton("Sim",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                	
	                	if(idSol != -1){
	        				
	        				params = new String[2][2];
	    					
	        				params[0][0] = "userId";
	    					params[0][1] = String.valueOf(idUsr);
	    					params[1][0] = "idDonation";
	    					params[1][1] = String.valueOf(idSol);
	    					
	    					webservice = new WebService("doacao_InserirNovaDoacao", params);
	    					thread = new Thread(getActivity(), webservice, getInterface());
	    					
	    					dialog.cancel();
	    					thread.execute();
	        			}
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
        
		return rootView;
		
	}

	@Override
	public void returningCall(String result) {

		try{
			
			result = result.replace("[", "");
			result = result.replace("]", "");
			
			if(result.equalsIgnoreCase("true")){
				Configuracao.showDialog(getActivity(), "DoarSP", "Sua doação foi aberta. Para confirma-la, vá em Doações pendentes e efetue o Checkin.", true);
			}
			else{
				Configuracao.showDialog(getActivity(), "DoarSP", "Não é possivel abrir essa Doação. Verifique se já não foi aberta ou se você está apto a doar.", true);
			}
			
		}
		catch(Exception e){
			e.getMessage();
		}
		
	}
	
	/** Getters and Setters **/
	
	public InterfaceListener getInterface(){
		return this;
	}
	
	/** Getters and Setters End **/
}
