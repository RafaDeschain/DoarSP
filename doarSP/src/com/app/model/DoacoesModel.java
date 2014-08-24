package com.app.model;

import java.util.Date;
import android.content.Context;


public class DoacoesModel {
	
	private int CodDoacao, CodSolicitacao, CodUsuarioDoador;
	private Date DtdDoacao;
	
	public DoacoesModel(Context context){
		
		
	}
	
	public DoacoesModel(){
		
	}
	
	public int getCodDoacao(){
		
		return CodDoacao;
	}
	
	public void setCodDoacao(int CodDoacao ){
		
		this.CodDoacao = CodDoacao;
	}
	
	public int getCodSolicitacao(){
		
		return CodSolicitacao;
	}
	
	public void setCodSolicitacao(int CodSolicitacao){
		
		this.CodSolicitacao = CodSolicitacao;
	}
	
	public int getCodUsuarioDoador(){
		
		return CodUsuarioDoador;
	}
	
	public void setCodUsuarioDoador(int CodUsuarioDoador){
		
		this.CodUsuarioDoador = CodUsuarioDoador;
	}
	
	public Date getDtdDoacao(){
		
		return  DtdDoacao;
	}
	
	public void setDtdDoacao(Date DtdDoacao){
		
		this.DtdDoacao =  DtdDoacao;
	}

}
