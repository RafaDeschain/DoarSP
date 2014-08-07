package com.app.model;

import android.content.Context;

public class MuralModel {
	
	private int CodMural, CodSolicitacao, imagem;
	private String Comentario ;
	
	public MuralModel(int CodMural, int CodSolicitacao, String Comentario, int imagem  ){
		
		super();
		this.CodMural = CodMural;
		this.CodSolicitacao = CodSolicitacao;
		this.Comentario = Comentario;
		this.imagem = imagem;
		
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
	
	 public int getImagem() {
		 
	    return imagem;
	        
	 }
	 
	  public void setImagem(int imagem) {
		  
		this.imagem = imagem;
	 } 
	
	

}
