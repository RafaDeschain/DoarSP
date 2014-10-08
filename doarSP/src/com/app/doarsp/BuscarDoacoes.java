package com.app.doarsp;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class BuscarDoacoes extends Fragment {
	
	TextView versionInfo;
	private TabHost tabHost;
	private View rootView;
	private Hemocentros hemocentro;
	private FeedDoacoes feed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_buscardoacoes,
				container, false);
		
		tabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		tabHost.addTab(criarTab("feed", R.string.bd_feed, R.id.feed));
		tabHost.addTab(criarTab("mapa", R.string.bd_mapa, R.id.mapa));
		
		carregarMapa();
		
		carregarFeed();
		
		return rootView;
	}
	
	private void carregarMapa()
	{
		hemocentro = new Hemocentros();
		Utils.trocarFragment(hemocentro, getActivity().getFragmentManager(), R.id.mapa);
	}
	
	private void carregarFeed()
	{
		feed = new FeedDoacoes();
		Utils.trocarFragment(feed, getActivity().getFragmentManager(), R.id.feed);
	}
	
	private TabSpec criarTab(String tag, int nome, int local)
	{
		TabSpec tabSpec = tabHost.newTabSpec(tag);
		tabSpec.setIndicator(getString(nome));
		tabSpec.setContent(local);
		return tabSpec;
	}
	
	public void onDestroyView() {
		// Necessario destruir o mapa se não dá vazamento de memória e erro ao dar inflate novamente.
		Fragment fragment = (getFragmentManager().findFragmentById(R.id.mapa));
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
		hemocentro.onDestroyView();
		feed.onDestroyView();
		super.onDestroyView();
    }
}