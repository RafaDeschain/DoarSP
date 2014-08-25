package com.app.model;

import android.content.Context;

public class SolicitacoesModel {
	
	private int CodSolicitacao, CodUsuarioSolicitante, QtnDoacoes, 
				QtnDoacoesRealizadas, HemocentroResp, HemocentroRespEx, idade;
	
	private String postoDoacao, nome, tipoSanguineo;
				
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
	
	public int getIdade(){
		
		return idade;
	}
	
	public void setIdade(int idade){
		
		this.idade = idade;
	}
	
	public String getNome(){
		
		return nome;
	}
	
	public void setNome(String nome){
		
		this.nome = nome;
		
	}
	
	public String getTipoSanguineo(){
		
		return tipoSanguineo;
	}
	
	public void setTipoSanguineo(String tipoSanguineo){
		
		this.tipoSanguineo = tipoSanguineo;
	}
	
	public String getpostoDoacao(){
		
		return postoDoacao;
	}
	
	public void setPostoDoacao(String postoDoacao){
		
		this.postoDoacao = postoDoacao;
	}
	
	
	
	
}
