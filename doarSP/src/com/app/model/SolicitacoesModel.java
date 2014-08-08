package com.app.model;

import com.app.DAO.*;

public class SolicitacoesModel {
	
	private int codSolicitacao, codUsuarioSolicitante, qtnDoacoes, 
				qtnDoacoesRealizadas, hemocentroResp, hemocentroRespEx;
	
	public SolicitacoesModel(){
		
	}

	public int getCodSolicitacao() {
		return codSolicitacao;
	}

	public void setCodSolicitacao(int codSolicitacao) {
		this.codSolicitacao = codSolicitacao;
	}

	public int getCodUsuarioSolicitante() {
		return codUsuarioSolicitante;
	}

	public void setCodUsuarioSolicitante(int codUsuarioSolicitante) {
		this.codUsuarioSolicitante = codUsuarioSolicitante;
	}

	public int getQtnDoacoes() {
		return qtnDoacoes;
	}

	public void setQtnDoacoes(int qtnDoacoes) {
		this.qtnDoacoes = qtnDoacoes;
	}

	public int getQtnDoacoesRealizadas() {
		return qtnDoacoesRealizadas;
	}

	public void setQtnDoacoesRealizadas(int qtnDoacoesRealizadas) {
		this.qtnDoacoesRealizadas = qtnDoacoesRealizadas;
	}

	public int getHemocentroResp() {
		return hemocentroResp;
	}

	public void setHemocentroResp(int hemocentroResp) {
		this.hemocentroResp = hemocentroResp;
	}
	
	public int getHemocentroRespEx() {
		return hemocentroRespEx;
	}

	public void setHemocentroRespEx(int hemocentroRespEx) {
		this.hemocentroRespEx = hemocentroRespEx;
	}
	
	
}
