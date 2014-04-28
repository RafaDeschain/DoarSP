package com.app.doarsp;

import com.app.model.UserModel;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
 
public class Principal extends Fragment {
	
	ImageView achivementPicture;
	TextView nameEdit, tpSanguineo, ultimaDoacao;
	CheckBox aptoDoar;
     
    public Principal(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);

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
        
        return rootView;
    }
}