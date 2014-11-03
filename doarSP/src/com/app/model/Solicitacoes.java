package com.app.model;

import android.content.Context;

public class Solicitacoes {
	
	private int codSolicitacao, codUsuario, codHemocentro, tipoSanguineo, qtdDoacoes;
	private String nomePaciente, comentarios;
				
	public Solicitacoes(Context context){}
	
	public Solicitacoes(){}
	
	public int getCodSolicitacao() {
		return codSolicitacao;
	}

	public void setCodSolicitacao(int codSolicitacao) {
		this.codSolicitacao = codSolicitacao;
	}

	public int getCodUsuario() {
		return codUsuario;
	}

	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}

	public int getCodHemocentro() {
		return codHemocentro;
	}

	public void setCodHemocentro(int codHemocentro) {
		this.codHemocentro = codHemocentro;
	}

	public int getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(int tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public int getQtdDoacoes() {
		return qtdDoacoes;
	}

	public void setQtdDoacoes(int qtdDoacoes) {
		this.qtdDoacoes = qtdDoacoes;
	}

	public String getNomePaciente() {
		return nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
}
