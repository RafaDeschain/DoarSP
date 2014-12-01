package com.app.model;

public class Mural {
	
	private int codDoacao, tpSanguineo;
	private String nomePaciente, comentario;
	
	public Mural(int codDoacao, int tpSanguineo, String nomePaciente,
			String comentario) {
		super();
		this.codDoacao = codDoacao;
		this.tpSanguineo = tpSanguineo;
		this.nomePaciente = nomePaciente;
		this.comentario = comentario;
	}

	public int getCodDoacao() {
		return codDoacao;
	}

	public void setCodDoacao(int codDoacao) {
		this.codDoacao = codDoacao;
	}

	public int getTpSanguineo() {
		return tpSanguineo;
	}

	public void setTpSanguineo(int tpSanguineo) {
		this.tpSanguineo = tpSanguineo;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}