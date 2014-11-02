package com.app.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.app.DAO.ApapterDAO;

public class User {
	
	private int codUsuario, tpSanguineo; 
	private boolean notificacaoPush, notificacaoEmail, statusApto;
	private String nome, eMail, dtdUltimaDoacao, dtdNascimento;
	private ApapterDAO DAO;
	private byte[] imageAchivement;
	
	//Login
	private String login, senha;
	private boolean isLoggedIn;
	
	public User(Context context){
		DAO = new ApapterDAO(context);
		dtdNascimento = "";
		dtdUltimaDoacao = "";
	}

	public User(){
	 
	}
	
	public int getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	public int getTpSanguineo() {
		return tpSanguineo;
	}
	public void setTpSanguineo(int tpSanguineo) {
		this.tpSanguineo = tpSanguineo;
	}
	public boolean getNotificacaoPush() {
		return notificacaoPush;
	}
	public void setNotificacaoPush(boolean notificacaoPush) {
		this.notificacaoPush = notificacaoPush;
	}
	public boolean getNotificacaoEmail() {
		return notificacaoEmail;
	}
	public void setNotificacaoEmail(boolean notificacaoEmail) {
		this.notificacaoEmail = notificacaoEmail;
	}
	public boolean getStatusApto() {
		return statusApto;
	}
	public void setStatusApto(boolean statusApto) {
		this.statusApto = statusApto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getDtdUltimaDoacao() {
		return dtdUltimaDoacao;
	}
	public void setDtdUltimaDoacao(String dtdUltimaDoacao) {
		this.dtdUltimaDoacao = dtdUltimaDoacao;
	}
	
	@SuppressLint("SimpleDateFormat")
	public String getDtdNascimento() {							
		return dtdNascimento;
	}
	
	public void setDtdNascimento(String dtdNascimento) {
		this.dtdNascimento = dtdNascimento;
	}
	
	public Bitmap getImageAchivement() {		
		return BitmapFactory.decodeByteArray(imageAchivement, 0, imageAchivement.length);
	}

	public void setImageAchivement(byte[] imageAchivement) {
		this.imageAchivement = imageAchivement;
	}
	
	public String getTpSanguineoAsString()
	{
		switch (getTpSanguineo()) {
		case 0: return "A";
		case 1: return "A-";
		case 2: return "B";
		case 3: return "B-";
		case 4:	return "AB";
		case 5: return "AB-";
		case 6: return "O";
		case 7: return "O-";
		default: return "Não Informado";
		}		
	}
	
	public boolean postInsert(Resources res)
	{		
		return DAO.posInsert(this, res);
	}	
	
	public boolean CheckIfExistsUser()
	{
		return DAO.CheckIfExistsUser();
	}
	
	public void getUserData(User userData)
	{
		DAO.getUserData(userData);
	}
	
	public boolean postUpdateUser(User userData)
	{
		return DAO.postUpdate(userData);
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
