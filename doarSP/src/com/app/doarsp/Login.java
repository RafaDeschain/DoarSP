package com.app.doarsp;

import android.R.bool;
import android.UnusedStub;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.app.*;
import com.app.model.*;
import com.google.android.gms.drive.internal.GetMetadataRequest;

@SuppressWarnings("unused")
@SuppressLint("ValidFragment")

public class Login extends Fragment{
	
	Utils util;
	
	private UserModel loginModel;
	
	ActionBar actionBar;
    Principal principal;
    TextView loginErro;
    EditText loginET, senhaET;
    
    WebService webservice;
    String[][] params;
    AsyncTask<String, Void, String> retorno;
    
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
			
			//Caso n�o esteja, cria a tela de login
			else{
				
		        View regView = inflater.inflate(R.layout.fragment_login, container, false);
		        TextView btnRegistrar = (TextView) regView.findViewById(R.id.cadastroLinkTV);
		        Button btnLogin = (Button) regView.findViewById(R.id.loginBT);
		        
		        //Pega o login e a senha digitados
				loginET = (EditText) regView.findViewById(R.id.loginET);
				senhaET = (EditText) regView.findViewById(R.id.senhaET);
				
				//Cria a classe de modelo login
				loginModel = new UserModel();
		        
				//Cria o webservice
				webservice = new WebService();
				
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
	
	//starting asynchronus task
	 private class SoapAccessTask extends AsyncTask<String, Void, String> {
	      
	     @Override
	     protected void onPreExecute() {
	          //if you want, start progress dialog here
	     }
	          
	     @Override
	     protected String doInBackground(String... urls) {
	    	 return webservice.callWebService("usuario_Login", params);
	    }
	   
	    @Override
	    protected void onPostExecute(String result) {
	            //if you started progress dialog dismiss it here
	         }
	     }
	
		public Login(Context context){
			
		}
		
		//m�todo que valida o login do usu�rio
		public boolean validaLogin(String login, String senha){
			
			if (login.equals("") || senha.equals("")){
				return false;
			}else{
				
				loginModel.setLogin(login);
				loginModel.setSenha(senha);
				
				params = new String[2][2];
				
				params[0][0] = "userName";
				params[0][1] = loginModel.getLogin();
				params[1][0] = "password";
				params[1][1] = loginModel.getSenha();
				
				SoapAccessTask task = new SoapAccessTask();
				
				try
				{				
				  task.execute();
				  return true;
				}
				catch(Exception ex)
				{
				  return false;
				}
			}
		}
		
		
		//M�todo que cria a sess�o
		public void criarSessao(String email){
			
		}
		
		//M�todo que checa sess�o
		public boolean checarSessao(){
		        return false;
		}
		
		//M�todo que destroi a sess�o
		public void destroiSessao(){
			
		}
		
		/** M�todos dos bot�es **/
		
		View.OnClickListener registrarUsuario = new View.OnClickListener() {
			public void onClick(View v) {
				Utils.hideKeyboard((EditText)getActivity().getCurrentFocus(), getActivity());
				RegistrarUsuario regUsr = new RegistrarUsuario();
				Utils.trocarFragment(regUsr, getFragmentManager(), true);
			}
		};
		
		View.OnClickListener loginBtn = new View.OnClickListener() {
			public void onClick(View v) {
				
				//Valida se h� algo escrito neles
				if(validaLogin(loginET.getText().toString(), senhaET.getText().toString()) == true){
					
					Utils.hideKeyboard((EditText)getActivity().getCurrentFocus(), getActivity());
					
					//Login com sucesso, vai para a tela principal
					Principal principal = new Principal();
					Utils.trocarFragment(principal, getFragmentManager(), false);
				}
				else{
					loginErro.setVisibility(View.VISIBLE);
				}
			}
		};
		
		/** Fim M�todos dos bot�es **/
}
