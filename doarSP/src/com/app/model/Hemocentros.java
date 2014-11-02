package com.app.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.app.DAO.ApapterDAO;
import com.app.doarsp.ArrayPostos;

public class Hemocentros {
	private int codPosto;
	private String endPosto, telPosto, nomePosto;
	private double latitude, longitude;
	private ApapterDAO DAO;
	private ArrayList<Solicitacoes> solicitacoes;
	
	public Hemocentros(Context context){
		DAO = new ApapterDAO(context);		
	}	
	
	public Hemocentros(int codPosto, String nomePosto, String endPosto){
		this.codPosto 	= codPosto;
		this.nomePosto 	= nomePosto;
		this.endPosto 	= endPosto;
	}
	
	public Hemocentros(){
		 
	}
	
	public int getCodPosto() {
		return codPosto;
	}
	public void setCodPosto(int codPosto) {
		this.codPosto = codPosto;
	}
	public String getEndPosto() {
		return endPosto;
	}
	public void setEndPosto(String endPosto) {
		this.endPosto = endPosto;
	}
	public String getTelPosto() {
		return telPosto;
	}
	public void setTelPosto(String telPosto) {
		this.telPosto = telPosto;
	}
	public String getNomePosto() {
		return nomePosto;
	}
	public void setNomePosto(String nomePosto) {
		this.nomePosto = nomePosto;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public boolean checkPosto()
	{
		return DAO.checkPostos();	 
	}
	
	public void initializeValuesInBd()
	{
		String[][] postosValues = ArrayPostos.postosDoacao;
		DAO.initializeValuesInBd(postosValues);
	}
	
	public Cursor getAllPostos()
	{
		return DAO.getAllPostos();
	}
	
	// Metodos de Solicitacao
	public void setSolicitacoes(ArrayList<Solicitacoes> solicitacoes)
	{
		this.solicitacoes = solicitacoes;
	}
	
	public ArrayList<Solicitacoes> getSolicitacoes()
	{
		return this.solicitacoes;
	}
	
	public void addSolicitacao(Solicitacoes solicitacao)
	{
		if(solicitacoesIsNull())
			setSolicitacoes(new ArrayList<Solicitacoes>());
		this.solicitacoes.add(solicitacao);
	}
	
	public Solicitacoes getSolicitacao(int indice)
	{
		return this.solicitacoes.get(indice);
	}
	
	public boolean solicitacoesIsNull()
	{
		return (this.solicitacoes == null);
	}
}
