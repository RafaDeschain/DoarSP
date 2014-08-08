package com.app.model;

import java.util.Date;
import android.content.Context;


public class DoacoesModel {
	
	private int codDoacao, codSolicitacao, codUsuarioDoador;
	private Date dtdDoacao;
	
	public DoacoesModel(Context context){
		
		
	}
	
	public DoacoesModel(){
		
	}
	
	public int getCodDoacao(){
		
		return codDoacao;
	}
	
	public void setCodDoacao(int codDoacao ){
		
		this.codDoacao = codDoacao;
	}
	
	public int getCodSolicitacao(){
		
		return codSolicitacao;
	}
	
	public void setCodSolicitacao(int codSolicitacao){
		
		this.codSolicitacao = codSolicitacao;
	}
	
	public int getCodUsuarioDoador(){
		
		return codUsuarioDoador;
	}
	
	public void setCodUsuarioDoador(int codUsuarioDoador){
		
		this.codUsuarioDoador = codUsuarioDoador;
	}
	
	public Date getDtdDoacao(){
		
		return dtdDoacao;
	}
	
	public void setDtdDoacao(Date dtdDoacao){
		
		this.dtdDoacao =  dtdDoacao;
	}

}
