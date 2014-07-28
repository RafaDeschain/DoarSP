package com.app.doarsp;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.model.UserModel;

import com.app.doarsp.R;

public class RegistrarUsuario extends Fragment {
	
	UserModel UserData;
    EditText nameEdit, eMailEdit, dataNasEdit;
    Spinner tpSanguineo;
    CheckBox notificaoPush, notificaoEmail;
    Context context;
    ActionBar actionBar;
    Utils util;
    
	public RegistrarUsuario(){
		//Construtor em branco
	}
	
	View.OnClickListener saveBtnHandlerClickForInsert = new View.OnClickListener() {
		public void onClick(View v) {
			String eMail    = eMailEdit.getText().toString();
			String dataNasc = dataNasEdit.getText().toString();				

			UserData.setNome(nameEdit.getText().toString());
			UserData.setTpSanguineo(tpSanguineo.getSelectedItemPosition());
			UserData.seteMail(eMail);
			UserData.setDtdNascimento(dataNasc);
			UserData.setNotificacaoPush((notificaoPush.isChecked() ? 1 : 0));
			UserData.setNotificacaoEmail((notificaoEmail.isChecked() ? 1 : 0));
			Resources res = getResources();
			
			if (Utils.validadeValues(context, dataNasc, eMail) && (UserData.postInsert(res))) {
				Utils.showMessage(context, "Cadastro Efetuado com Sucesso", 0);
				Utils.enableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
/**
		View rootView = inflater.inflate(R.layout.fragment_registrar, container, false);
		context = rootView.getContext();
		UserData = new UserModel(context);
		
		UserData.getUserData(UserData);
		
		nameEdit = (EditText)rootView.findViewById(R.id.);		
		tpSanguineo = (Spinner)rootView.findViewById(R.id.);
		eMailEdit = (EditText)rootView.findViewById(R.id.);
		dataNasEdit = (EditText)rootView.findViewById(R.id.);
		notificaoPush = (CheckBox)rootView.findViewById(R.id.);
		notificaoEmail = (CheckBox)rootView.findViewById(R.id.);
		Button btnSalvar = (Button) rootView.findViewById(R.id.);
				
		btnSalvar.setOnClickListener(saveBtnHandlerClickForInsert);
		CharSequence mFirstTitle = "Doe sorrisos :)";
		getActivity().getActionBar().setTitle(mFirstTitle);
		
		return rootView;
		**/
		
		View rootView = inflater.inflate(R.layout.fragment_registrar, container, false);
		actionBar = getActivity().getActionBar();
		actionBar.setTitle("Registre-se");
		dataNasEdit = (EditText)rootView.findViewById(R.id.RegistrarDadosNascimento);
		dataNasEdit.addTextChangedListener(Utils.insert("##/##/####", dataNasEdit));
		return rootView;
	}
}