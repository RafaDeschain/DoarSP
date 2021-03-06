package com.app.doarsp;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.model.*;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;

@SuppressLint("ValidFragment")

public class Login extends Fragment implements InterfaceListener{
	
	private User userModel;
	
	private ActionBar actionBar;
	private TextView loginErro;
	private EditText loginET, senhaET;
	private String login, senha;

	/** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;	
	public AlertDialog alertDialog;
	
	public Login(){}
    
	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
			View regView = inflater.inflate(R.layout.fragment_login, container, false);
	        TextView btnRegistrar = (TextView) regView.findViewById(R.id.cadastroLinkTV);
	        Button btnLogin = (Button) regView.findViewById(R.id.loginBT);
	        
	        //Pega o login e a senha digitados
			loginET = (EditText) regView.findViewById(R.id.loginET);
			senhaET = (EditText) regView.findViewById(R.id.senhaET);
			
	        btnRegistrar.setOnClickListener(registrarUsuario);
	        btnLogin.setOnClickListener(loginBtn);
	        
	        actionBar = getActivity().getActionBar();
	        actionBar.setTitle("Login");
	        
	        //Deixa a mensagem de erro invisivel
			loginErro = (TextView) regView.findViewById(R.id.loginerroTV);
			loginErro.setVisibility(View.INVISIBLE);
	        
	        Configuracao.disableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
			
			//Verifica se o usuario ja esta logado
			if(checarSessao() == true){
				recuperaSessao();
			}
			
		    return regView;
		}
	
		/** M�todos dos bot�es **/
		
		View.OnClickListener registrarUsuario = new View.OnClickListener() {
			public void onClick(View v) {
				
				Configuracao.hideKeyboard(getActivity());
				RegistrarUsuario regUsr = new RegistrarUsuario();
				Configuracao.trocarFragment(regUsr, getFragmentManager(), true);
				
			}
		};
		
		View.OnClickListener loginBtn = new View.OnClickListener() {
			public void onClick(View v) {
				
				loginErro.setVisibility(View.INVISIBLE);
				
				String login, senha;
				login = loginET.getText().toString();
				senha = senhaET.getText().toString();
				
				if (login.equals("") || senha.equals("")){
					returningCall("false");
				}
				else{
					
					Configuracao.hideKeyboard(getActivity());
					
					params = new String[2][2];
					
					setLogin(login);
					setSenha(senha);
					
					params[0][0] = "username";
					params[0][1] = getLogin();
					params[1][0] = "password";
					params[1][1] = Configuracao.encripta(getSenha());
					
					setWebservice(new WebService("usuario_Login", params));
					
					/**
					 * Cria uma nova Thread, necess�ria para fazer a requisi��o no WebService
					 * Recebe como parametros a Activity, o WebService criado e a Interface Listener
					 * Ap�s executar, ele retorna o resultado para o m�todo returningCall()
					 */
					
					thread = new Thread(getActivity(), getWebservice(), getInterface());
					thread.execute();
				}
			}
		};
		
		/** Fim M�todos dos bot�es **/
		
		/** M�todo que recebe o retorno do WebService **/
		
		@Override
		public void returningCall(String result) {
			
			if(result.equalsIgnoreCase("false")){
				loginErro.setVisibility(View.VISIBLE);
			}
			else if(result.equalsIgnoreCase("Erro")){
				if(checarSessao() == true){
					User user = new User(getActivity());
					user.deleteUser();
				}
				Configuracao.showDialog(getActivity(), "Oops..", "Verifique sua conex�o de internet", true);
			}
			else{
				//Login com sucesso, vai para a tela principal
				
				result = result.replace("[", "");
				result = result.replace("]", "");
				
				try{
					
					JSONObject json = new JSONObject(result);
					
					//Cria a classe de modelo login					

					userModel = new User(getActivity());
					userModel.setCodUsuario			(json.getInt("codUsuario"));
					userModel.setTpSanguineo		(json.getInt("tpSanguineo"));
					userModel.setNome				(json.getString("nome"));
					userModel.seteMail				(json.getString("eMail"));
					userModel.setNotificacaoPush	((json.getInt("notificacaoPush") == 1 ? true : false));
					userModel.setNotificacaoEmail	((json.getInt("notificaoEmail") == 1 ? true : false));
					userModel.setStatusApto			((json.getInt("statusApto") == 1 ? true : false));
					userModel.setDtdUltimaDoacao	(json.getString("ultimaDoacao"));
					userModel.setDtdNascimento		(json.getString("dtdNascimento"));
					userModel.setLogin				(getLogin());
					userModel.setSenha				(getSenha());
					userModel.setIsLoggedIn			(true);
					
					MainActivity global = (MainActivity) getActivity();
					global.setUser(userModel);
					
					if(checarSessao() == false){
						userModel.userInsert();
					}
					
					Principal principal = new Principal();
					Configuracao.trocarFragment(principal, getFragmentManager(), false);
					
				}
				catch(Exception e){
					Configuracao.showDialog(getActivity(), "Oops", "Ocorreu um erro durante o seu login", true);
				}
			}
		}
		
		/** Class Methods **/
		
		public boolean checarSessao(){
			User user = new User(getActivity());
			return user.CheckIfExistsUser();
		}
		
		public void recuperaSessao(){
			
			User user = new User(getActivity());
			user.getUserData();
			setLogin(user.getLogin());
			setSenha(user.getSenha());
			
			params = new String[2][2];
			
			params[0][0] = "username";
			params[0][1] = getLogin();
			params[1][0] = "password";
			params[1][1] = Configuracao.encripta(getSenha());
			
			setWebservice(new WebService("usuario_Login", params));
			
			/**
			 * Cria uma nova Thread, necess�ria para fazer a requisi��o no WebService
			 * Recebe como parametros a Activity, o WebService criado e a Interface Listener
			 * Ap�s executar, ele retorna o resultado para o m�todo returningCall()
			 */
			
			thread = new Thread(getActivity(), getWebservice(), getInterface());
			thread.execute();
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
		
		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}

		public String getSenha() {
			return senha;
		}

		public void setSenha(String senha) {
			this.senha = senha;
		}
		
		/** Getters and Setters End **/
}
