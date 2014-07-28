package com.app.doarsp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	
	/** Variaveis da Sessão **/
	
	// Preferencias
    SharedPreferences pref;
    
    // Editor das preferencias
    Editor editor;
   
    // Context
    Context _context;
    
    // Shared pref mode
    int PRIVATE_MODE = 0;
    
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";
    
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn"; 
    // Email do usuário
    public static final String KEY_EMAIL = "email";
    
    /** Fim Variaveis da Sessão **/
    
    ActionBar actionBar;
    
	
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        View regView = inflater.inflate(R.layout.fragment_login, container, false);
	        TextView btnRegistrar = (TextView) regView.findViewById(R.id.cadastroLinkTV);
	        Button btnLogin = (Button) regView.findViewById(R.id.loginBT);
	        
	        btnRegistrar.setOnClickListener(registrarUsuario);
	        btnLogin.setOnClickListener(loginBtn);
	        
	        actionBar = getActivity().getActionBar();
	        actionBar.setTitle("Login");
	        return regView;
	    }
	
		public Login(Context context){
			this._context = context;
	        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	        editor = pref.edit();
		}
		
		//método que valida o login do usuário
		public boolean validaLogin(){
			return true;
		}
		
		
		//Método que cria a sessão
		public void criarSessao(String email){
			
			editor.putBoolean(IS_LOGIN, true);
			editor.putString(KEY_EMAIL, email);
			editor.commit();
			
		}
		
		//Método que checa sessão
		public boolean checarSessao(){
		        return pref.getBoolean(IS_LOGIN, false);
		}
		
		//Método que destroi a sessão
		public void destroiSessao(){
			
			editor.clear();
			editor.commit();
			
		}
		
		/** Métodos dos botões **/
		
		View.OnClickListener registrarUsuario = new View.OnClickListener() {
			public void onClick(View v) {
				RegistrarUsuario regUsr = new RegistrarUsuario();
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.frame_container, regUsr);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		};
		
		View.OnClickListener loginBtn = new View.OnClickListener() {
			public void onClick(View v) {
				
				//if(validaLogin() == true) {}
				
				Principal principal = new Principal();
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.frame_container, principal);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		};
		
		/** Fim Métodos dos botões **/
		
		/** Métodos set e get da classe **/
		
		public void setLogin(String login){
			this.login = login;
		}
		
		public void setSenha(String senha){
			this.senha = util.toSHA1(senha.getBytes());
		}
		
		public String getLogin(){
			return login;
		}
		
		public String getSenha(){
			return senha;
		}
		
		/** Fim Métodos set e get da classe **/
}
