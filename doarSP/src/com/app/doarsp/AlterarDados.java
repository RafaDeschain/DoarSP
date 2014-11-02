package com.app.doarsp;

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

import com.app.model.User;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

import com.app.doarsp.R;

public class AlterarDados extends Fragment implements InterfaceListener{
	
	private User user;
	private EditText nameEdit, eMailEdit, dataNasEdit, usuario, senha, senha2;
	private Spinner tpSanguineo;
	private CheckBox notificaoPush, notificaoEmail;
    
    /** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;	
	public AlertDialog alertDialog;
	
	//Modelo
	User userModel;
    
	public AlterarDados(){
		//Construtor em branco
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_alterardados, container, false);
		
		MainActivity global = (MainActivity)getActivity();
        user = global.getUser();
		
		nameEdit 			= (EditText)rootView.findViewById(R.id.AlterarDadosNome);
		eMailEdit 			= (EditText)rootView.findViewById(R.id.AlterarDadosEmail);
		tpSanguineo 		= (Spinner)rootView.findViewById(R.id.AlterarDadosTipo);
		usuario				= (EditText)rootView.findViewById(R.id.AlterarDadosUsername);
		senha				= (EditText)rootView.findViewById(R.id.AlterarDadosSenha);
		senha2				= (EditText)rootView.findViewById(R.id.AlterarDadosSenha2);
		dataNasEdit 		= (EditText)rootView.findViewById(R.id.AlterarDadosNascimento);
		notificaoPush 		= (CheckBox)rootView.findViewById(R.id.AlterarDadosPush);
		notificaoEmail 		= (CheckBox)rootView.findViewById(R.id.AlterarDadosEmailNot);
		Button btnSalvar 	= (Button) rootView.findViewById(R.id.AlterarDadosSalvar);
		
		nameEdit.setText(user.getNome());
		eMailEdit.setText(user.geteMail());
		tpSanguineo.setSelection(user.getTpSanguineo());
		usuario.setText(user.getLogin());
		usuario.setEnabled(false);
		dataNasEdit.setText(user.getDtdNascimento());
		notificaoPush.setChecked(user.getNotificacaoPush());
		notificaoEmail.setChecked(user.getNotificacaoEmail());
		btnSalvar.setOnClickListener(saveBtnHandlerClickForUpdate);
		
		return rootView;
	}
	
	View.OnClickListener saveBtnHandlerClickForUpdate = new View.OnClickListener() {
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
			if(Configuracao.isEmpty(senha)){
				senha.setError("Por favor, escolha uma senha");
				valido = false;
			}
			if(!senha.getText().toString().equals(senha.getText().toString())){
				senha.setError("As senhas não conferem");
				senha.requestFocus();
				senha2.setError("");
				valido = false;
			}
			
			if (valido == true) {
				
				//Transforma os EditTexts para String
				String nameString 			= nameEdit.getText().toString();
				String eMailString 			= eMailEdit.getText().toString();
				String senhaString 			= senha.getText().toString();
				int tpSanguineoInt 			= tpSanguineo.getSelectedItemPosition();
				String dataNasString 		= dataNasEdit.getText().toString();
				boolean notificaoPushVal 	= notificaoPush.isChecked();
				boolean notificaoEmailVal 	= notificaoEmail.isChecked();
				
				params = new String[8][2];
				
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
				params[5][0] = "dtdNascimento";
				params[5][1] = dataNasString;
				params[6][0] = "password";
				params[6][1] = senhaString;
				params[7][0] = "CodUser";
				params[7][1] = String.valueOf(user.getCodUsuario());
				
				setWebservice(new WebService("usuario_AtualizaUsuario", params));
				
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

	@Override
	public void returningCall(String result) {
		
		if(result.equalsIgnoreCase("true")){
			
			MainActivity global = (MainActivity) getActivity();
			userModel = global.getUser();
			
			userModel.setNome(nameEdit.getText().toString());
			userModel.seteMail(eMailEdit.getText().toString());
			userModel.setSenha(senha.getText().toString());
			userModel.setTpSanguineo(tpSanguineo.getSelectedItemPosition());
			userModel.setDtdNascimento(dataNasEdit.getText().toString());
			userModel.setNotificacaoPush(notificaoPush.isChecked());
			userModel.setNotificacaoEmail(notificaoEmail.isChecked());
			
			global.setUser(userModel);
			
			Configuracao.showDialog(getActivity(), "Sucesso", "Seus dados foram atualizados", true);
		}
		else{
			Configuracao.showDialog(getActivity(), "Oops..", "Ocorreu um erro durante a atualização", true);
		}
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