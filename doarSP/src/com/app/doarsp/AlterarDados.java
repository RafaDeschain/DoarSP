package com.app.doarsp;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
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
    
	public AlterarDados() {		
	}
	View.OnClickListener saveBtnHandlerClickForInsert = new View.OnClickListener() {
	  public void onClick(View v) {
		UserData.setNome(nameEdit.getText().toString());
		UserData.setTpSanguineo(tpSanguineo.getSelectedItemPosition());
		UserData.seteMail(eMailEdit.getText().toString());
		UserData.setDtdNascimento(dataNasEdit.getText().toString());		
		UserData.setNotificacaoPush((notificaoPush.isChecked() ? 1 : 0));
		UserData.setNotificacaoEmail((notificaoEmail.isChecked() ? 1 : 0));
		Resources res = getResources();
		
		
		if (UserData.postInsert(res)) {
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
		
		nameEdit = (EditText)rootView.findViewById(R.id.AlterarDadosNome);
		tpSanguineo = (Spinner)rootView.findViewById(R.id.AlterarDadosTipo);
		eMailEdit = (EditText)rootView.findViewById(R.id.AlterarDadosEmail);
		dataNasEdit = (EditText)rootView.findViewById(R.id.AlterarDadosNascimento);
		notificaoPush = (CheckBox)rootView.findViewById(R.id.AlterarDadosPush);
		notificaoEmail = (CheckBox)rootView.findViewById(R.id.AlterarDadosEmailNot);
		if (!UserData.CheckIfExistsUser())
		{
			Button btnSalvar = (Button) rootView.findViewById(R.id.AlterarDadosSalvar);
			btnSalvar.setOnClickListener(saveBtnHandlerClickForInsert);
		}		
		
		return rootView;
	}
}