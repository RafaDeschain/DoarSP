package com.app.doarsp;

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

public class AlterarDados extends Fragment {
	
	UserModel UserData;
    EditText nameEdit, eMailEdit, dataNasEdit;
    Spinner tpSanguineo;
    CheckBox notificaoPush, notificaoEmail;
    Context context;
    
	public AlterarDados(){
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
	
	View.OnClickListener saveBtnHandlerClickForUpdate = new View.OnClickListener() {
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
			}			
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_alterardados, container, false);
		context = rootView.getContext();
		UserData = new UserModel(context);
		
		UserData.getUserData(UserData);
		
		nameEdit = (EditText)rootView.findViewById(R.id.AlterarDadosNome);		
		tpSanguineo = (Spinner)rootView.findViewById(R.id.AlterarDadosTipo);
		eMailEdit = (EditText)rootView.findViewById(R.id.AlterarDadosEmail);
		dataNasEdit = (EditText)rootView.findViewById(R.id.AlterarDadosNascimento);
		notificaoPush = (CheckBox)rootView.findViewById(R.id.AlterarDadosPush);
		notificaoEmail = (CheckBox)rootView.findViewById(R.id.AlterarDadosEmailNot);
		Button btnSalvar = (Button) rootView.findViewById(R.id.AlterarDadosSalvar);
		
		if (!UserData.CheckIfExistsUser())
		{			
			btnSalvar.setOnClickListener(saveBtnHandlerClickForInsert);
			CharSequence mFirstTitle = "Bem vindo ao DoarSP";
			getActivity().getActionBar().setTitle(mFirstTitle);
		}		
		else
		{
			nameEdit.setText(UserData.getNome());
			tpSanguineo.setSelection(UserData.getTpSanguineo());
			eMailEdit.setText(UserData.geteMail());
			dataNasEdit.setText(UserData.getDtdNascimento());
			notificaoPush.setChecked((UserData.getNotificacaoPush() == 1 ? true : false));
			notificaoEmail.setChecked(UserData.getNotificacaoEmail() == 1 ? true : false);
			btnSalvar.setOnClickListener(saveBtnHandlerClickForUpdate);
		}
		
		return rootView;
	}
}