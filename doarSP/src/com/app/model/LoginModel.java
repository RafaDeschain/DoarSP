package com.app.model;

import com.app.*;
import com.app.doarsp.Utils;

public class LoginModel extends UserModel{

	private String login, senha;
	private boolean isLoggedIn;

	public LoginModel(){
		
	}
	
	public LoginModel(String login, String senha){
		setLogin(login);
		setSenha(senha);
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
	
	public boolean getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	//Verifica se o usuário está logado
	public boolean isLoggedIn(){
		return true;
	}
}
