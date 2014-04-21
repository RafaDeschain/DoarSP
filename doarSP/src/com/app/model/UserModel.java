package com.app.model;

import java.util.Date;
import android.content.Context;
import com.app.DAO.ApapterDAO;

public class UserModel {
	private int codUsuario, tpSanguineo, NotificacaoPush, NotificacaoEmail, StatusApto; 
	private String nome, eMail;
	private String dtdUltimaDoacao, dtdNascimento;
	private ApapterDAO DAO;
	
	public UserModel(Context context){
		DAO = new ApapterDAO(context); 
	}

	public UserModel(){
	 
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
	public int getNotificacaoPush() {
		return NotificacaoPush;
	}
	public void setNotificacaoPush(int notificacaoPush) {
		NotificacaoPush = notificacaoPush;
	}
	public int getNotificacaoEmail() {
		return NotificacaoEmail;
	}
	public void setNotificacaoEmail(int notificacaoEmail) {
		NotificacaoEmail = notificacaoEmail;
	}
	public int getStatusApto() {
		return StatusApto;
	}
	public void setStatusApto(int statusApto) {
		StatusApto = statusApto;
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
	public String getDtdNascimento() {
		return dtdNascimento;
	}
	public void setDtdNascimento(String dtdNascimento) {
		this.dtdNascimento = dtdNascimento;
	}	
	
	public UserModel postValues()
	{
		return DAO.postValues(this);
	}	

}