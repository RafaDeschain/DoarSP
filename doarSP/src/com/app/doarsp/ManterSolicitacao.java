package com.app.doarsp;

import com.app.model.SolicitacoesModel;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class ManterSolicitacao extends Fragment {
	
	SolicitacoesModel solicitacao;
	EditText nomeEdite, idadeEdite;
	Spinner tpSanguineo, pstDoacao, qtdDoacao;
	
	
	
	public ManterSolicitacao()
	{
		
	}
	
	View.OnClickListener saveBtnInsert = new View.OnClickListener() {
	public void onClick(View v) {
			boolean campo = true;
			
			if(Utils.isEmpty(nomeEdite)){
				nomeEdite.setError("Por favor, preencha o nome");
				campo = false;
			}
			
			if(Utils.isEmpty(idadeEdite)){
				idadeEdite.setError("Por favor, preencha a idade");
				campo = false;
			}
			
			if(campo == true)
			{
				solicitacao.setNome(nomeEdite.getText().toString());
				solicitacao.setIdade(Integer.parseInt((idadeEdite.getText().toString())));
				solicitacao.setQtnDoacoes(qtdDoacao.getSelectedItemPosition());
				solicitacao.setPostoDoacao(Integer.toString(pstDoacao.getSelectedItemPosition()));
				solicitacao.setTipoSanguineo(Integer.toString(tpSanguineo.getSelectedItemPosition()));
				
				Utils.showMessage(getActivity().getApplicationContext(), "Solicitação efetuado com sucesso", 0);    
				
			}
		

			
		}
	};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
			View rootView = inflater.inflate(R.layout.fragment_mantersolicitacao, container, false);
			
			solicitacao = new SolicitacoesModel();
			nomeEdite = (EditText)rootView.findViewById(R.id.NomePacienteSolicitacao);
			idadeEdite = (EditText)rootView.findViewById(R.id.solicitacaoidIdade);
			tpSanguineo = (Spinner)rootView.findViewById(R.id.solicitacaoDadosTipo);
			pstDoacao = (Spinner)rootView.findViewById(R.id.solicitacaoDadosHemo);
			qtdDoacao = (Spinner)rootView.findViewById(R.id.solicitacaoQuantidadeDoacao);
			Button btnSalvar = (Button) rootView.findViewById(R.id.SolicitacaoSalvar);
			
			btnSalvar.setOnClickListener(saveBtnInsert);
			
		return rootView;
		
	}


}


