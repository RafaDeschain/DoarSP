package com.app.doarsp;

import com.app.model.SolicitacoesModel;
import com.app.webservice.InterfaceListener;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class NovaSolicitacao extends Fragment implements InterfaceListener{
	
	private SolicitacoesModel solicitacao;
	private EditText nomeEdit;
	private Spinner tpSanguineo, pstDoacao, qtdDoacao;
	
	public NovaSolicitacao(){}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			View rootView = inflater.inflate(R.layout.fragment_novasolicitacao, container, false);
			
			solicitacao 		= new SolicitacoesModel();
			nomeEdit 			= (EditText)rootView.findViewById(R.id.SolicitacaoNome);
			tpSanguineo 		= (Spinner)rootView.findViewById(R.id.SolicitacaoTipo);
			pstDoacao 			= (Spinner)rootView.findViewById(R.id.SolicitacaoHemocentro);
			qtdDoacao 			= (Spinner)rootView.findViewById(R.id.SolicitacaoQuantidade);
			Button btnSalvar 	= (Button) rootView.findViewById(R.id.SolicitacaoSalvar);
			
			btnSalvar.setOnClickListener(saveBtnInsert);
			
		return rootView;
		
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


