package com.app.model;

import android.content.Context;
import android.database.Cursor;

import com.app.DAO.ApapterDAO;
import com.app.doarsp.ArrayPostos;

public class HemocentrosModel {
	private int CodPosto;
	private String EndPosto, TelPosto, NomePosto;
	private double Latitude, Longitude;
	private ApapterDAO DAO;
	
	public HemocentrosModel(Context context){
		DAO = new ApapterDAO(context);		
	}	
	
	public HemocentrosModel(){
		 
	}
	
	public int getCodPosto() {
		return CodPosto;
	}
	public void setCodPosto(int codPosto) {
		CodPosto = codPosto;
	}
	public String getEndPosto() {
		return EndPosto;
	}
	public void setEndPosto(String endPosto) {
		EndPosto = endPosto;
	}
	public String getTelPosto() {
		return TelPosto;
	}
	public void setTelPosto(String telPosto) {
		TelPosto = telPosto;
	}
	public String getNomePosto() {
		return NomePosto;
	}
	public void setNomePosto(String nomePosto) {
		NomePosto = nomePosto;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
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
}
