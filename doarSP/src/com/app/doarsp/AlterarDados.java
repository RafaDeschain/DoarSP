package com.app.doarsp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ToggleButton;

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
		
		
		EditText nameEdit = (EditText)rootView.findViewById(R.id.editText1);
		nameEdit.setText(UserData.getNome());
		
		Spinner tpSanguineo = (Spinner)rootView.findViewById(R.id.spinner1);
		tpSanguineo.setSelection(2);
		
		EditText eMailEdit = (EditText)rootView.findViewById(R.id.editText2);
		eMailEdit.setText(UserData.geteMail());
		
		EditText dataNasEdit = (EditText)rootView.findViewById(R.id.editText3);
		dataNasEdit.setText(UserData.getDtdNascimento());
		
		ToggleButton notificaoPush = (ToggleButton)rootView.findViewById(R.id.toggleButton1);
		notificaoPush.setActivated(true);
		
		ToggleButton notificaoEmail = (ToggleButton)rootView.findViewById(R.id.toggleButton2);
		notificaoEmail.setActivated(false);
		
		return rootView;
	}
}