package com.app.model;

public class Solicitacoes {
	
	public Solicitacoes(int idSolicitacao, int idUsuario,
			int quantidadeSolicitacoes, int quantidadesRealizadas,
			int idHemocentro, int tipoSanguineo, int status, String nome,
			String data, String comentario) {
		super();
		this.idSolicitacao = idSolicitacao;
		this.idUsuario = idUsuario;
		this.quantidadeSolicitacoes = quantidadeSolicitacoes;
		this.quantidadesRealizadas = quantidadesRealizadas;
		this.idHemocentro = idHemocentro;
		this.tipoSanguineo = tipoSanguineo;
		this.status = status;
		this.nome = nome;
		this.data = data;
		this.comentario = comentario;
	}

	private int idSolicitacao, idUsuario, 
				quantidadeSolicitacoes, 
				quantidadesRealizadas, 
				idHemocentro, tipoSanguineo, status;
	
	private String nome, data, comentario;
	
	public int getIdSolicitacao() {
		return idSolicitacao;
	}
	public void setIdSolicitacao(int idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getQuantidadeSolicitacoes() {
		return quantidadeSolicitacoes;
	}
	public void setQuantidadeSolicitacoes(int quantidadeSolicitacoes) {
		this.quantidadeSolicitacoes = quantidadeSolicitacoes;
	}
	public int getQuantidadesRealizadas() {
		return quantidadesRealizadas;
	}
	public void setQuantidadesRealizadas(int quantidadesRealizadas) {
		this.quantidadesRealizadas = quantidadesRealizadas;
	}
	public int getIdHemocentro() {
		return idHemocentro;
	}
	public void setIdHemocentro(int idHemocentro) {
		this.idHemocentro = idHemocentro;
	}
	public int getTipoSanguineo() {
		return tipoSanguineo;
	}
	public void setTipoSanguineo(int tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
