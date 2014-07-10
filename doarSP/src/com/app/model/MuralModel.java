package com.app.model;

import android.content.Context;

public class MuralModel {
	
	private int CodMural, CodSolicitacao;
	private String Comentario ;
	
	public MuralModel(Context context){
		
		
	}
	
	public MuralModel(){
		
		
	}
	
	public int getCodMural(){
		
		return CodMural;
	}
	
	public void setCodMural(int CodMural){
		
		this.CodMural = CodMural;
	}
	
	public int getCodSolicitacao(){
		
		return CodSolicitacao;
	}
	
	public void setCodSolicitacao(int CodSolicitacao){
		
		this.CodSolicitacao = CodSolicitacao;
	}
	
	public String getComentario(){
		
		return Comentario;
	}
	
	public void setComentario(String Comentario){
		
		this.Comentario = Comentario;
	}

}
