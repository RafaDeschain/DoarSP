package com.app.doarsp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.model.UserModel;

import com.app.doarsp.R;

public class AlterarDados extends Fragment {

	public AlterarDados() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_alterardados, container, false);		
		UserModel UserData = new UserModel(rootView.getContext());
		
		UserData.setNome("Teste");
		UserData.seteMail("teste@gmail.com");
		UserData.setDtdNascimento("01/01/1990");
		
		
		EditText nameEdit = (EditText)rootView.findViewById(R.id.AlterarDadosNome);
		nameEdit.setText(UserData.getNome());
		
		Spinner tpSanguineo = (Spinner)rootView.findViewById(R.id.AlterarDadosTipo);
		tpSanguineo.setSelection(2);
		
		EditText eMailEdit = (EditText)rootView.findViewById(R.id.AlterarDadosEmail);
		eMailEdit.setText(UserData.geteMail());
		
		EditText dataNasEdit = (EditText)rootView.findViewById(R.id.AlterarDadosNascimento);
		dataNasEdit.setText(UserData.getDtdNascimento());
		
		CheckBox notificaoPush = (CheckBox)rootView.findViewById(R.id.AlterarDadosPush);
		notificaoPush.setActivated(true);
		
		CheckBox notificaoEmail = (CheckBox)rootView.findViewById(R.id.AlterarDadosEmailNot);
		notificaoEmail.setActivated(false);
		
		return rootView;
	}
}