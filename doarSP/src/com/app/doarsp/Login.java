package com.app.doarsp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.drive.internal.GetMetadataRequest;

@SuppressLint("ValidFragment")
public class Login extends Fragment{
	
	Utils util;
	
	private String login, senha;
	
	ActionBar actionBar;
    Principal principal;
    TextView loginErro;
    EditText loginET, senhaET;
    
	
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
			
			//Verifica se o usuario ja esta logado
			if(checarSessao() == true){
				View regView = inflater.inflate(R.layout.fragment_principal, container, false);
				principal = new Principal();
				Utils.trocarFragment(principal, getFragmentManager());
				return regView;
			}
			
			//Caso não esteja, cria a tela de login
			else{
				
		        View regView = inflater.inflate(R.layout.fragment_login, container, false);
		        TextView btnRegistrar = (TextView) regView.findViewById(R.id.cadastroLinkTV);
		        Button btnLogin = (Button) regView.findViewById(R.id.loginBT);
		        
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
	
		public Login(Context context){
			
		}
		
		//método que valida o login do usuário
		public boolean validaLogin(String login, String senha){
			
			if (login.equals("") || senha.equals("")){
				return false;
			}else{
				setLogin(login);
				setSenha(senha);
				return true;
			}
		}
		
		
		//Método que cria a sessão
		public void criarSessao(String email){
			
		}
		
		//Método que checa sessão
		public boolean checarSessao(){
		        return false;
		}
		
		//Método que destroi a sessão
		public void destroiSessao(){
			
		}
		
		/** Métodos dos botões **/
		
		View.OnClickListener registrarUsuario = new View.OnClickListener() {
			public void onClick(View v) {
				RegistrarUsuario regUsr = new RegistrarUsuario();
				Utils.trocarFragment(regUsr, getFragmentManager());
			}
		};
		
		View.OnClickListener loginBtn = new View.OnClickListener() {
			public void onClick(View v) {
				
				//Pega o login e a senha digitados
				loginET = (EditText) getView().findViewById(R.id.loginET);
				senhaET = (EditText) getView().findViewById(R.id.senhaET);
				
				//Valida se há algo escrito neles
				if(validaLogin(loginET.getText().toString(), senhaET.getText().toString()) == true){
					//Login com sucesso, vai para a tela principal
					Principal principal = new Principal();
					Utils.trocarFragment(principal, getFragmentManager());
				}
				else{
					loginErro.setVisibility(View.VISIBLE);
				}
			}
		};
		
		/** Fim Métodos dos botões **/
		
		/** Métodos set e get da classe **/
		
		public void setLogin(String login){
			this.login = login;
		}
		
		public void setSenha(String senha){
			//this.senha = util.toSHA1(senha.getBytes());
			this.senha = senha;
		}
		
		public String getLogin(){
			return login;
		}
		
		public String getSenha(){
			return senha;
		}
		
		/** Fim Métodos set e get da classe **/
}
