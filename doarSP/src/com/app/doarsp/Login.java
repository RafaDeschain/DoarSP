package com.app.doarsp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
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
	
	Utils util;
	
	private UserModel loginModel;
	
	private ActionBar actionBar;
	private Principal principal;
	private TextView loginErro;
	private EditText loginET, senhaET;
    
	private WebService webservice;
	private String[][] params;	
	public AlertDialog alertDialog;
	
	public Login(Context context){
		
	}
    
	@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		
			//Verifica se o usuario ja esta logado
			if(checarSessao() == true){
				
				View regView = inflater.inflate(R.layout.fragment_principal, container, false);
				principal = new Principal();
				Utils.trocarFragment(principal, getFragmentManager(), false);
				return regView;
			}
			
			//Caso não esteja, cria a tela de login
			else{
					
		        View regView = inflater.inflate(R.layout.fragment_login, container, false);
		        TextView btnRegistrar = (TextView) regView.findViewById(R.id.cadastroLinkTV);
		        Button btnLogin = (Button) regView.findViewById(R.id.loginBT);
		        
		        //Pega o login e a senha digitados
				loginET = (EditText) regView.findViewById(R.id.loginET);
				senhaET = (EditText) regView.findViewById(R.id.senhaET);
				
				//Cria a classe de modelo login
				loginModel = new UserModel();
		        
		        btnRegistrar.setOnClickListener(registrarUsuario);
		        btnLogin.setOnClickListener(loginBtn);
		        
		        actionBar = getActivity().getActionBar();
		        actionBar.setTitle("Login");
		        
		        //Deixa a mensagem de erro invisivel
				loginErro = (TextView) regView.findViewById(R.id.loginerroTV);
				loginErro.setVisibility(View.INVISIBLE);
		        
		        Utils.disableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
		        return regView;
			}
		}
		
		/** Método que recebe o retorno do WebService **/
		
		@Override
		public void returningCall(String result) {
			
			if(result.equalsIgnoreCase("true")){
				//Login com sucesso, vai para a tela principal
				Utils.hideKeyboard(getActivity());
				Principal principal = new Principal();
				Utils.trocarFragment(principal, getFragmentManager(), false);
			}
			else if(result.equalsIgnoreCase("false")){
				loginErro.setVisibility(View.VISIBLE);
			}
			else{
				Utils.showDialog(getActivity(), "Oops..", "Verifique sua conexão de internet", true);
			}
		}
		
		/** Métodos dos botões **/
		
		View.OnClickListener registrarUsuario = new View.OnClickListener() {
			public void onClick(View v) {
				
				Utils.hideKeyboard(getActivity());
				RegistrarUsuario regUsr = new RegistrarUsuario();
				Utils.trocarFragment(regUsr, getFragmentManager(), true);
				
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
					
					Utils.hideKeyboard(getActivity());
					
					loginModel.setLogin(login);
					loginModel.setSenha(senha);
					
					params = new String[2][2];
					
					params[0][0] = "userName";
					params[0][1] = loginModel.getLogin();
					params[1][0] = "password";
					params[1][1] = loginModel.getSenha();
					
					setWebservice(new WebService("login_Usuario", params));
					
					/**
					 * Cria uma nova Thread, necessária para fazer a requisição no WebService
					 * Recebe como parametros a Activity, o WebService criado e a Interface Listener
					 * Após executar, ele retorna o resultado para o método returningCall()
					 */
					
					Thread thread = new Thread(getActivity(), getWebservice(), getInterface());
					thread.execute();
				}
			}
		};
		
		/** Fim Métodos dos botões **/
		
		/** Class Methods **/
		
		public boolean checarSessao(){
		        return false;
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
