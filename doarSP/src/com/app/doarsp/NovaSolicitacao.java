package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import com.app.adapter.ListaHemocentrosAdapter;
import com.app.model.Hemocentros;
import com.app.model.Solicitacoes;
import com.app.webservice.InterfaceListener;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class NovaSolicitacao extends Fragment implements InterfaceListener{
	
	private Solicitacoes solicitacao;
	private EditText nomeEdit;
	private Spinner tpSanguineo, qtdDoacao;
	private ListView pstDoacao;
	
	public NovaSolicitacao(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			View rootView = inflater.inflate(R.layout.fragment_novasolicitacao, container, false);
			
			solicitacao 		= new Solicitacoes();
			nomeEdit 			= (EditText)rootView.findViewById(R.id.SolicitacaoNome);
			tpSanguineo 		= (Spinner)rootView.findViewById(R.id.SolicitacaoTipo);
			pstDoacao 			= (ListView)rootView.findViewById(R.id.SolicitacaoHemocentro);
			qtdDoacao 			= (Spinner)rootView.findViewById(R.id.SolicitacaoQuantidade);
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
					
					//Configuracao.showDialog(getActivity(), "", "" + position, true);
					view.setSelected(true);
				}
			});
			
			btnSalvar.setOnClickListener(saveBtnInsert);
			
		return rootView;
		
	}
	
	private List<Hemocentros> gerarHemocentros() {
		
		List<Hemocentros> lista = new ArrayList<Hemocentros>();
		lista.add(criarHemocentro(1, "Teste nome", "End a"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
		lista.add(criarHemocentro(2, "Teste nome 2", "End b"));
         
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
			
			if(campo == true)
			{
				
				solicitacao.setNome(nomeEdit.getText().toString());
				solicitacao.setQtnDoacoes(qtdDoacao.getSelectedItemPosition());
				solicitacao.setPostoDoacao(Integer.toString(pstDoacao.getSelectedItemPosition()));
				solicitacao.setTipoSanguineo(Integer.toString(tpSanguineo.getSelectedItemPosition()));
				
				Configuracao.showMessage(getActivity().getApplicationContext(), "Solicitação efetuado com sucesso", 0);    
				
			}
		}
	};
	
	@Override
	public void returningCall(String result) {
		// TODO Auto-generated method stub
		
	}
}


