package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.ListaHemocentrosAdapter;
import com.app.model.Hemocentros;
import com.app.model.Solicitacoes;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class NovaSolicitacao extends Fragment implements InterfaceListener{
	
	private Solicitacoes solicitacao;
	private EditText nomeEdit, comentarios;
	private Spinner tpSanguineo, qtdDoacao;
	private ListView pstDoacao;
	
	private int idHemo = -1;
	
	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;	
	public AlertDialog alertDialog;
	
	public NovaSolicitacao(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			View rootView = inflater.inflate(R.layout.fragment_novasolicitacao, container, false);
			
			solicitacao 		= new Solicitacoes();
			nomeEdit 			= (EditText)rootView.findViewById(R.id.SolicitacaoNome);
			tpSanguineo 		= (Spinner)rootView.findViewById(R.id.SolicitacaoTipo);
			pstDoacao 			= (ListView)rootView.findViewById(R.id.SolicitacaoHemocentro);
			qtdDoacao 			= (Spinner)rootView.findViewById(R.id.SolicitacaoQuantidade);
			comentarios			= (EditText)rootView.findViewById(R.id.SolicitacaoComentario);
			Button btnSalvar 	= (Button) rootView.findViewById(R.id.SolicitacaoSalvar);
			
			List<Hemocentros> hemo = gerarHemocentros();
	          
	        final ListaHemocentrosAdapter hemoAdapter = new ListaHemocentrosAdapter(getActivity(),  hemo);
	        pstDoacao.setAdapter(hemoAdapter);
	        pstDoacao.setSelector(R.drawable.list_selector_hemocentros);
	        
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
	      
	      pstDoacao.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3){
					
					TextView id = (TextView) view.findViewById(R.id.ListaIdHemocentro);
					idHemo = Integer.parseInt((String) id.getText());
					view.setSelected(true);
				}
			});
			
			btnSalvar.setOnClickListener(saveBtnInsert);
			
		return rootView;
		
	}
	
	private List<Hemocentros> gerarHemocentros() {
		
		//Instancia o objeto que faz o retorno dos postos.
		Hemocentros postos = new Hemocentros(getActivity().getApplicationContext());
		
		//Query que vai retornar os postos que devem ser iterados nas marcações.
		Cursor query = postos.getAllPostos();
		query.moveToFirst();
		List<Hemocentros> lista = new ArrayList<Hemocentros>();
		
		for (int i = 0; i < query.getCount(); i ++)
		{
			lista.add(criarHemocentro(query.getInt(0) + 1, query.getString(2), query.getString(1)));
			query.moveToNext();
		}
		
        return lista;
    }
     
    private Hemocentros criarHemocentro(int id, String nome, String endereco) {
    	Hemocentros hemocentro = new Hemocentros(id, nome, endereco);
        return hemocentro;
    }
	
	View.OnClickListener saveBtnInsert = new View.OnClickListener() {
	public void onClick(View v) {
			boolean campo = true;
			
			if(Configuracao.isEmpty(nomeEdit)){
				nomeEdit.setError("Por favor, preencha o nome");
				campo = false;
			}
			
			if(idHemo == -1){
				Configuracao.showDialog(getActivity(), "Solicitação", "Por favor, escolha um hemocentro", true);
				campo = false;
			}
			
			if(campo == true)
			{
				AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
	            builder1.setMessage("Deseja realmente criar essa solicitação?");
	            builder1.setCancelable(true);
	            builder1.setPositiveButton("Sim",
	                    new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                	
	                	MainActivity global = (MainActivity)getActivity();
						
						solicitacao.setCodUsuario(global.getUser().getCodUsuario());
						solicitacao.setNomePaciente(nomeEdit.getText().toString());
						solicitacao.setQtdDoacoes(Integer.parseInt(qtdDoacao.getSelectedItem().toString()));
						solicitacao.setCodHemocentro(idHemo);
						solicitacao.setTipoSanguineo(tpSanguineo.getSelectedItemPosition());
						solicitacao.setComentarios(comentarios.getText().toString());
						
						params = new String[6][2];
						
						params[0][0] = "userId";
						params[0][1] =  String.valueOf(solicitacao.getCodUsuario());
						params[1][0] = "qtnNecessaria";
						params[1][1] = String.valueOf(solicitacao.getQtdDoacoes());
						params[2][0] = "idHemoCentro";
						params[2][1] = String.valueOf(solicitacao.getCodHemocentro());
						params[3][0] = "tpSanguineo";
						params[3][1] = String.valueOf(solicitacao.getTipoSanguineo());
						params[4][0] = "pacienteNome";
						params[4][1] = solicitacao.getNomePaciente();
						params[5][0] = "comentario";
						params[5][1] = solicitacao.getComentarios();
						
						setWebservice(new WebService("solicitacao_InserirNovaSolicitacao", params));
						
						/**
						 * Cria uma nova Thread, necessária para fazer a requisição no WebService
						 * Recebe como parametros a Activity, o WebService criado e a Interface Listener
						 * Após executar, ele retorna o resultado para o método returningCall()
						 */
						dialog.cancel();
						
						thread = new Thread(getActivity(), getWebservice(), getInterface());
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
		}
	};
	
	@Override
	public void returningCall(String result) {
		MainActivity global = (MainActivity)getActivity();
		
		if(result.equalsIgnoreCase(Integer.toString(global.getUser().getCodUsuario()))){
			Configuracao.showDialog(getActivity(), "Sucesso", "Sua solicitação foi aberta com sucesso", true);
			Principal fragment = new Principal();
			Configuracao.trocarFragment(fragment, getFragmentManager(), false);
		}
		else{
			Configuracao.showDialog(getActivity(), "Oops", "Ocorreu um erro ao abrir sua solicitação", true);
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
	
	/** Getters and Setters End **/
}


