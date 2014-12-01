package com.app.model;

public class Doacao {

	private int idDoacao, idSolicitacao, idHemocentro;
	private String nomePaciente;
	
	public Doacao(int idDoacao, int idSolicitacao, int idHemocentro,
			String nomePaciente) {
		super();
		this.idDoacao 		= idDoacao;
		this.idSolicitacao 	= idSolicitacao;
		this.idHemocentro 	= idHemocentro;
		this.nomePaciente 	= nomePaciente;
	}
	
	public int getIdDoacao() {
		return idDoacao;
	}
	public void setIdDoacao(int idDoacao) {
		this.idDoacao = idDoacao;
	}
	public int getIdSolicitacao() {
		return idSolicitacao;
	}
	public void setIdSolicitacao(int idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	public int getIdHemocentro() {
		return idHemocentro;
	}
	public void setIdHemocentro(int idHemocentro) {
		this.idHemocentro = idHemocentro;
	}
	public String getNomePaciente() {
		return nomePaciente;
	}
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
}
