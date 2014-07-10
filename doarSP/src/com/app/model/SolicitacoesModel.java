package com.app.model;

import android.content.Context;

public class SolicitacoesModel {
	
	private int CodSolicitacao, CodUsuarioSolicitante, QtnDoacoes, 
				QtnDoacoesRealizadas, HemocentroResp, HemocentroRespEx;
				
	public SolicitacoesModel(Context context){
		
		
	}
	
	public SolicitacoesModel(){
		
		
	}
	
	public int getCodSolicitacao(){
		
		return CodSolicitacao;
	}
	
	public void setCodSolicitacao(int CodSolicitacao){
		
		this.CodSolicitacao = CodSolicitacao;
	}
	
	public int getCodUsuarioSolicitante(){
		
		return CodUsuarioSolicitante;
	}
	
	public void setCodUsuarioSolicitante(int CodUsuarioSolicitante){
		
		this.CodUsuarioSolicitante = CodUsuarioSolicitante;
	}
	
	public int getQtnDoacoes(){
		
		return QtnDoacoes;
	}
	
	public void setQtnDoacoes(int QtnDoacoes){
		
		this.QtnDoacoes = QtnDoacoes;
	}
	
	public int getQtnDoacoesRealizadas(){
		
		return QtnDoacoesRealizadas;
	}
	
	public void setQtnDoacoesRealizadas(int QtnDoacoesRealizadas){
		
		this.QtnDoacoesRealizadas = QtnDoacoesRealizadas;
	}
	
	public int getHemocentroResp(){
		
		return HemocentroResp;
	}
	
	public void setHemocentroResp(int HemocentroResp){
		
		this.HemocentroResp = HemocentroResp;
	}
	
	public int getHemocentroRespEx(){
		
		return HemocentroRespEx;
	}
	
	public void setHemocentroRespEx(int HemocentroRespEx){
		
		this.HemocentroRespEx = HemocentroRespEx;
	}
	
	
}
