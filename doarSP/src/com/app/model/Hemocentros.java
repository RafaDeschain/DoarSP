package com.app.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.app.DAO.AppDAO;
import com.app.doarsp.ArrayPostos;

public class Hemocentros {
	private int codPosto;
	private String endPosto, telPosto, nomePosto;
	private double latitude, longitude;
	private AppDAO DAO;
	
	public Hemocentros(Context context){
		DAO = new AppDAO(context);		
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
		DAO.initializePostosInBd(postosValues);
	}
	
	public Cursor getAllPostos()
	{
		return DAO.getAllPostos();
	}
}
