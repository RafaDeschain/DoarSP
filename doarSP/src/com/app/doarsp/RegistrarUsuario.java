package com.app.doarsp;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	UserModel userModel;
    EditText nameEdit, eMailEdit, dataNasEdit, senhaEdit;
    Spinner tpSanguineo;
    CheckBox notificaoPush, notificaoEmail;
    ActionBar actionBar;
    Utils util;
    Button btnSalvar;
	
	View.OnClickListener saveBtnHandlerClickForInsert = new View.OnClickListener() {
		public void onClick(View v) {
			
			boolean valido = true;
			boolean dataValida = true;
			
			if(Utils.isEmpty(nameEdit)){
				nameEdit.setError("Por favor, preencha o nome");
				valido = false;
			}
			if(Utils.validaEmail(eMailEdit) == false){
				valido = false;
			}
			if(Utils.validaData(dataNasEdit) == false){
				valido = false;
			}
			if(Utils.isEmpty(senhaEdit)){
				senhaEdit.setError("Por favor, escolha uma senha");
				valido = false;
			}
			
			if (valido == true) {
				
				//Transforma os EditTexts para String
				String nameString = nameEdit.getText().toString();
				String eMailString = eMailEdit.getText().toString();
				String senhaString = senhaEdit.getText().toString();
				int tpSanguineoInt = tpSanguineo.getSelectedItemPosition();
				String dataNasString = dataNasEdit.getText().toString();
				boolean notificaoPushVal = notificaoPush.isChecked();
				boolean notificaoEmailVal = notificaoEmail.isChecked();
				
				//Seta a classe de modelo
				userModel = new UserModel();
				userModel.setNome(nameString);
				userModel.seteMail(eMailString);
				userModel.setSenha(senhaString);
				userModel.setTpSanguineo(tpSanguineoInt);
				userModel.setDtdNascimento(dataNasString);
				userModel.setNotificacaoPush(notificaoPushVal);
				userModel.setNotificacaoEmail(notificaoEmailVal);
				
				
				Utils.showMessage(getActivity().getApplicationContext(), "Cadastro Efetuado com Sucesso", 0);
				//Utils.enableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
			}
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View registrar = inflater.inflate(R.layout.fragment_registrar, container, false);
		
		actionBar = getActivity().getActionBar();
		actionBar.setTitle("Registre-se");
		dataNasEdit = (EditText)registrar.findViewById(R.id.RegistrarDadosNascimento);
		dataNasEdit.addTextChangedListener(Utils.insert("##/##/####", dataNasEdit));
		
		nameEdit = (EditText)registrar.findViewById(R.id.RegistrarDadosNome);
		eMailEdit = (EditText)registrar.findViewById(R.id.RegistrarDadosEmail);
		senhaEdit = (EditText)registrar.findViewById(R.id.RegistrarSenha);
		tpSanguineo = (Spinner)registrar.findViewById(R.id.RegistrarDadosTipo);
		dataNasEdit = (EditText)registrar.findViewById(R.id.RegistrarDadosNascimento);
		notificaoPush = (CheckBox)registrar.findViewById(R.id.RegistrarDadosPush);
		notificaoEmail = (CheckBox)registrar.findViewById(R.id.RegistrarDadosEmailNot);
		btnSalvar = (Button) registrar.findViewById(R.id.RegistrarDadosSalvar);
				
		btnSalvar.setOnClickListener(saveBtnHandlerClickForInsert);
		
		return registrar;
	}
}