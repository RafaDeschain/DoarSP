package com.app.doarsp;

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.app.*;

@SuppressLint("ValidFragment")
public class Login extends Fragment{
	
	Utils util;
	
	private String login, senha;
	
	/** Variaveis da Sess�o **/
	
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
     
    // Email do usu�rio
    public static final String KEY_EMAIL = "email";
    
    /** Fim Variaveis da Sess�o **/
	
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        return inflater.inflate(R.layout.fragment_login, container, false);
	    }
	
		public Login(Context context){
			this._context = context;
	        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
	        editor = pref.edit();
		}
		
		//m�todo que valida o login do usu�rio
		public boolean validaLogin(){
			return true;
		}
		
		
		//M�todo que cria a sess�o
		public void criarSessao(String email){
			
			editor.putBoolean(IS_LOGIN, true);
			editor.putString(KEY_EMAIL, email);
			editor.commit();
			
		}
		
		//M�todo que checa sess�o
		public boolean checarSessao(){
		        return pref.getBoolean(IS_LOGIN, false);
		}
		
		//M�todo que destroi a sess�o
		public void destroiSessao(){
			
			editor.clear();
			editor.commit();
			
		}
		
		/** M�todos set e get da classe **/
		
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
		
		/** Fim M�todos set e get da classe **/
}
