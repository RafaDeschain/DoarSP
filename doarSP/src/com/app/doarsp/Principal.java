package com.app.doarsp;

import java.util.List;

import com.app.adapter.ListaMuralAdapter;
import com.app.model.MuralModel;
import com.app.model.UserModel;

import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class Principal extends Fragment {
	
	ImageView achivementPicture;
	TextView nameEdit, tpSanguineo, ultimaDoacao;
	CheckBox aptoDoar;
	Configuracao util;
    ActionBar actionBar;
	
    public Principal(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        Configuracao.enableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
        
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Principal");
             
        Mural mu = new Mural();
        ListView listView = (ListView)rootView.findViewById(R.id.lista_doacoes);
		List<MuralModel> mural = mu.gerarDoacaoMSG();
		final ListaMuralAdapter muralAdapter = new ListaMuralAdapter(getActivity(), mural);
		listView.setAdapter(muralAdapter);
		
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
        
/**
        // Carrega os componentes
        achivementPicture = (ImageView)rootView.findViewById(R.id.PrincipalImageView);
        nameEdit 		  = (TextView)rootView.findViewById(R.id.PrincipalNome);
        tpSanguineo 	  = (TextView)rootView.findViewById(R.id.PrincipalTIpoSanguineo);
        ultimaDoacao	  = (TextView)rootView.findViewById(R.id.PrincipalUltimaDoacao);
        aptoDoar 	      = (CheckBox)rootView.findViewById(R.id.PrincipalAptoDoar);
                
        // Carrega os dados no banco
        UserModel userData = new UserModel(rootView.getContext());
        userData.getUserData(userData);
        
        // Seta os dados do banco nos componentes
        Bitmap achivementBitmap = userData.getImageAchivement();        
        if (achivementBitmap.getByteCount() > 0)
        	achivementPicture.setImageBitmap(achivementBitmap);        
        nameEdit.setText(userData.getNome());
        tpSanguineo.setText(userData.getTpSanguineoAsString());
        // Ainda nao esta feito o recurso de alterar a ultima doacao fixado o nascimento
        ultimaDoacao.setText(userData.getDtdNascimento());
        aptoDoar.setChecked(true);
        **/
        return rootView;
    }
}