package com.app.doarsp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import com.app.doarsp.R;

public class RegistrarUsuario extends Fragment implements InterfaceListener{
	
	private EditText nameEdit, eMailEdit, dataNasEdit, senhaEdit, senhaEdit2, usernameEdit;
	private Spinner tpSanguineo;
	private CheckBox notificaoPush, notificaoEmail;
	private ActionBar actionBar;
	private Button btnSalvar;
    
    /** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;	
	public AlertDialog alertDialog;
	
	View.OnClickListener saveBtnHandlerClickForInsert = new View.OnClickListener() {
		public void onClick(View v) {
			
			Configuracao.hideKeyboard(getActivity());
			
			boolean valido = true;
			
			if(Configuracao.isEmpty(nameEdit)){
				nameEdit.setError("Por favor, preencha o nome");
				valido = false;
			}
			if(Configuracao.validaEmail(eMailEdit) == false){
				valido = false;
			}
			if(Configuracao.validaData(dataNasEdit) == false){
				valido = false;
			}
			if(Configuracao.isEmpty(senhaEdit)){
				senhaEdit.setError("Por favor, escolha uma senha");
				valido = false;
			}
			if(!senhaEdit.getText().toString().equals(senhaEdit2.getText().toString())){
				senhaEdit.setError("As senhas não conferem");
				senhaEdit.requestFocus();
				senhaEdit2.setError("");
				valido = false;
			}
			
			if (valido == true) {
				
				//Transforma os EditTexts para String
				String nameString 			= nameEdit.getText().toString();
				String eMailString 			= eMailEdit.getText().toString();
				String login				= usernameEdit.getText().toString();
				String senhaString 			= senhaEdit.getText().toString();
				int tpSanguineoInt 			= tpSanguineo.getSelectedItemPosition();
				String dataNasString 		= dataNasEdit.getText().toString();
				boolean notificaoPushVal 	= notificaoPush.isChecked();
				boolean notificaoEmailVal 	= notificaoEmail.isChecked();
				
				params = new String[9][2];
				
				params[0][0] = "tpSanguineo";
				params[0][1] =  String.valueOf(tpSanguineoInt);
				params[1][0] = "nome";
				params[1][1] = nameString;
				params[2][0] = "eMail";
				params[2][1] = eMailString;
				params[3][0] = "notificacaoPush";
				params[3][1] = String.valueOf(((notificaoPushVal) ? 1 : 0));
				params[4][0] = "notificacaoEmail";
				params[4][1] = String.valueOf(((notificaoEmailVal) ? 1 : 0));
				params[5][0] = "statusApto";
				params[5][1] = "1";
				params[6][0] = "dtdNascimento";
				params[6][1] = dataNasString;
				params[7][0] = "username";
				params[7][1] = login;
				params[8][0] = "password";
				params[8][1] = senhaString;
				
				setWebservice(new WebService("usuario_insereNovoUsuario", params));
				
				/**
				 * Cria uma nova Thread, necessária para fazer a requisição no WebService
				 * Recebe como parametros a Activity, o WebService criado e a Interface Listener
				 * Após executar, ele retorna o resultado para o método returningCall()
				 */
				
				thread = new Thread(getActivity(), getWebservice(), getInterface());
				thread.execute();
			}
		}
	};
	
	/** Método que recebe o retorno do WebService **/
	
	@Override
	public void returningCall(String result) {
		
		if(result.equalsIgnoreCase("true")){
			//Login com sucesso, vai para a tela principal
			Configuracao.hideKeyboard(getActivity());
			Login login = new Login(getActivity().getApplicationContext());
			Configuracao.trocarFragment(login, getFragmentManager(), false);
			Configuracao.showDialog(getActivity(), "Sucesso", "Cadastrado com sucesso", true);
		}
		else if(result.equalsIgnoreCase("false")){
			Configuracao.showDialog(getActivity(), "Oops..", "Ocorreu um erro durante o cadastro", true);
		}
		else{
			Configuracao.showDialog(getActivity(), "Oops..", "Verifique sua conexão de internet", true);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View registrar = inflater.inflate(R.layout.fragment_registrar, container, false);
		
		actionBar = getActivity().getActionBar();
		actionBar.setTitle("Registre-se");
		dataNasEdit = (EditText)registrar.findViewById(R.id.RegistrarDadosNascimento);
		dataNasEdit.addTextChangedListener(Configuracao.insert("##/##/####", dataNasEdit));

		nameEdit 		= (EditText)registrar.findViewById(R.id.RegistrarDadosNome);
		eMailEdit 		= (EditText)registrar.findViewById(R.id.RegistrarDadosEmail);
		usernameEdit	= (EditText)registrar.findViewById(R.id.RegistrarDadosUsername);
		senhaEdit 		= (EditText)registrar.findViewById(R.id.RegistrarSenha);
		senhaEdit2 		= (EditText)registrar.findViewById(R.id.RegistrarSenha2);
		tpSanguineo 	= (Spinner)	registrar.findViewById(R.id.RegistrarDadosTipo);
		dataNasEdit 	= (EditText)registrar.findViewById(R.id.RegistrarDadosNascimento);
		notificaoPush 	= (CheckBox)registrar.findViewById(R.id.RegistrarDadosPush);
		notificaoEmail 	= (CheckBox)registrar.findViewById(R.id.RegistrarDadosEmailNot);
		btnSalvar 		= (Button) 	registrar.findViewById(R.id.RegistrarDadosSalvar);

		btnSalvar.setOnClickListener(saveBtnHandlerClickForInsert);
		
		return registrar;
	}
	
	/** Getters and Setters **/
	
	public WebService getWebservice() {
		return webservice;
	}

	public void setWebservice(WebService webservice) {
		this.webservice = webservice;
	}
	
	public InterfaceListener getInterface(){
		return this;
	}
	
	/** Getters and Setters End **/
}