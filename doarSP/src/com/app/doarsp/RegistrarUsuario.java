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

import com.app.model.UserModel;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import com.app.doarsp.R;

public class RegistrarUsuario extends Fragment implements InterfaceListener{
	
	private UserModel userModel;
	private EditText nameEdit, eMailEdit, dataNasEdit, senhaEdit, usernameEdit;
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
			
			boolean valido = true;
			
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
				String nameString 			= nameEdit.getText().toString();
				String eMailString 			= eMailEdit.getText().toString();
				String username				= usernameEdit.getText().toString();
				String senhaString 			= senhaEdit.getText().toString();
				int tpSanguineoInt 			= tpSanguineo.getSelectedItemPosition();
				String dataNasString 		= dataNasEdit.getText().toString();
				boolean notificaoPushVal 	= notificaoPush.isChecked();
				boolean notificaoEmailVal 	= notificaoEmail.isChecked();
				
				//Seta a classe de modelo
				userModel = new UserModel();
				userModel.setNome(nameString);
				userModel.seteMail(eMailString);
				userModel.setLogin(username);
				userModel.setSenha(senhaString);
				userModel.setTpSanguineo(tpSanguineoInt);
				userModel.setDtdNascimento(dataNasString);
				userModel.setNotificacaoPush(notificaoPushVal);
				userModel.setNotificacaoEmail(notificaoEmailVal);
				
				params = new String[9][2];
				
				params[0][0] = "tpSanguineo";
				params[0][1] =  String.valueOf(userModel.getTpSanguineo());
				params[1][0] = "nome";
				params[1][1] = userModel.getNome();
				params[2][0] = "eMail";
				params[2][1] = userModel.geteMail();
				params[3][0] = "notificacaoPush";
				params[3][1] = String.valueOf(((userModel.getNotificacaoPush()) ? 1 : 0));
				params[4][0] = "notificacaoEmail";
				params[4][1] = String.valueOf(((userModel.getNotificacaoEmail()) ? 1 : 0));
				params[5][0] = "statusApto";
				params[5][1] = "1";
				params[6][0] = "dtdNascimento";
				params[6][1] = userModel.getDtdNascimento();
				params[7][0] = "username";
				params[7][1] = userModel.getLogin();
				params[8][0] = "password";
				params[8][1] = userModel.getSenha();
				
				setWebservice(new WebService("usuario_insereNovoUsuario", params));
				
				/**
				 * Cria uma nova Thread, necess�ria para fazer a requisi��o no WebService
				 * Recebe como parametros a Activity, o WebService criado e a Interface Listener
				 * Ap�s executar, ele retorna o resultado para o m�todo returningCall()
				 */
				
				thread = new Thread(getActivity(), getWebservice(), getInterface());
				try
				{
					thread.execute();
				}
				finally
				{
					if (thread.isCancelled())
					{
						thread.cancel(true);						
					}
				}
			}
		}
	};
	
	/** M�todo que recebe o retorno do WebService **/
	
	@Override
	public void returningCall(String result) {
		
		if(result.equalsIgnoreCase("true")){
			//Login com sucesso, vai para a tela principal
			Utils.hideKeyboard(getActivity());
			Login login = new Login(getActivity().getApplicationContext());
			Utils.trocarFragment(login, getFragmentManager(), false);
			Utils.showDialog(getActivity(), "Sucesso", "Cadastrado com sucesso", true);
		}
		else if(result.equalsIgnoreCase("false")){
			Utils.showDialog(getActivity(), "Oops..", "Ocorreu um erro durante o cadastro", true);
		}
		else{
			Utils.showDialog(getActivity(), "Oops..", "Verifique sua conex�o de internet", true);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View registrar = inflater.inflate(R.layout.fragment_registrar, container, false);
		
		actionBar = getActivity().getActionBar();
		actionBar.setTitle("Registre-se");
		dataNasEdit = (EditText)registrar.findViewById(R.id.RegistrarDadosNascimento);
		dataNasEdit.addTextChangedListener(Utils.insert("##/##/####", dataNasEdit));
		
		nameEdit 		= (EditText)registrar.findViewById(R.id.RegistrarDadosNome);
		eMailEdit 		= (EditText)registrar.findViewById(R.id.RegistrarDadosEmail);
		usernameEdit	= (EditText)registrar.findViewById(R.id.RegistrarDadosUsername);
		senhaEdit 		= (EditText)registrar.findViewById(R.id.RegistrarSenha);
		tpSanguineo 	= (Spinner)registrar.findViewById(R.id.RegistrarDadosTipo);
		dataNasEdit 	= (EditText)registrar.findViewById(R.id.RegistrarDadosNascimento);
		notificaoPush 	= (CheckBox)registrar.findViewById(R.id.RegistrarDadosPush);
		notificaoEmail 	= (CheckBox)registrar.findViewById(R.id.RegistrarDadosEmailNot);
		btnSalvar 		= (Button) registrar.findViewById(R.id.RegistrarDadosSalvar);
				
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