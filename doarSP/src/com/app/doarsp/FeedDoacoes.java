package com.app.doarsp;

import java.util.ArrayList;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.adapter.HemocentrosAdapter;
import com.app.model.HemocentrosModel;
import com.app.model.SolicitacoesModel;

public class FeedDoacoes extends Fragment {
	TextView versionInfo;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_feeddoacoes,
				container, false);
		
		ArrayList<HemocentrosModel> model = pegarHemocentros();
		HemocentrosAdapter adapter = new HemocentrosAdapter(getActivity(), model);
		
		// pegar ListView
		final ListView listaHemocentros = (ListView) rootView.findViewById(R.id.hemocentros);
		listaHemocentros.setAdapter(adapter);
		
		listaHemocentros.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3){
				// Nothing here
			}
		});
		
		return rootView;
	}
	
	public ArrayList<HemocentrosModel> pegarHemocentros()
	{
		ArrayList<HemocentrosModel> list = new ArrayList<HemocentrosModel>();
		
		HemocentrosModel model = new HemocentrosModel(getActivity());
		Cursor resultado = model.getAllPostos();
		
		if(resultado.isBeforeFirst())
			resultado.moveToFirst();
		
		while(!resultado.isLast())
		{
			// Move para o proximo registro
			resultado.moveToNext();
			
			// Criar Model temporário
			HemocentrosModel tempModel = new HemocentrosModel(getActivity());
			// Preenche Model
			tempModel.setCodPosto(resultado.getInt(0));
			tempModel.setEndPosto(resultado.getString(1));
			tempModel.setNomePosto(resultado.getString(2));
			tempModel.setLatitude(resultado.getDouble(3));
			tempModel.setLongitude(resultado.getDouble(4));
			// Adiciona model na lista
			list.add(tempModel);
		}
		
		return getHemocentroComSolicitacao(list);
	}
	

	private ArrayList<SolicitacoesModel> getSolicitacoes()
	{
		ArrayList<SolicitacoesModel> tempList = new ArrayList<SolicitacoesModel>();
		
		// Solicitações Temporárias
		SolicitacoesModel solic1 = new SolicitacoesModel();
		solic1.setCodSolicitacao(1);
		solic1.setQtnDoacoes(25);
		solic1.setQtnDoacoesRealizadas(5);
		solic1.setHemocentroResp(1);
		
		SolicitacoesModel solic2 = new SolicitacoesModel();
		solic2.setCodSolicitacao(2);
		solic2.setQtnDoacoes(10);
		solic2.setQtnDoacoesRealizadas(7);
		solic2.setHemocentroResp(1);
		
		SolicitacoesModel solic3 = new SolicitacoesModel();
		solic3.setCodSolicitacao(3);
		solic3.setQtnDoacoes(10);
		solic3.setQtnDoacoesRealizadas(2);
		solic3.setHemocentroResp(1);
		
		SolicitacoesModel solic4 = new SolicitacoesModel();
		solic4.setCodSolicitacao(3);
		solic4.setQtnDoacoes(10);
		solic4.setQtnDoacoesRealizadas(2);
		solic4.setHemocentroResp(2);
		
		tempList.add(solic1);
		tempList.add(solic2);
		tempList.add(solic3);
		tempList.add(solic4);
		
		return tempList;	
	}

	private ArrayList<HemocentrosModel> getHemocentroComSolicitacao(ArrayList<HemocentrosModel> hemocentros)
	{
		ArrayList<SolicitacoesModel> listaSolicitacoes = getSolicitacoes();
		ArrayList<HemocentrosModel> listaHemocentrosComSolicitacao = new ArrayList<HemocentrosModel>();
		
		for(HemocentrosModel hemocentro : hemocentros) {
			for (SolicitacoesModel solicitacao : listaSolicitacoes) {
				if(solicitacao.getHemocentroResp() == hemocentro.getCodPosto())
				{
					hemocentro.addSolicitacao(solicitacao);
				}
			}
			if(!hemocentro.solicitacoesIsNull())
			{
				listaHemocentrosComSolicitacao.add(hemocentro);
			}
		}
		return listaHemocentrosComSolicitacao;		
	}
}