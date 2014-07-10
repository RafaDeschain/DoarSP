package com.app.model;

import android.content.Context;

public class TipoSanguineoModel {
	
	private int CodTipoSanguineo;
	private String DescricaoTipoSanguineo;
	
	public TipoSanguineoModel(Context context){
		
		
	}
	
	public TipoSanguineoModel(){
		
		
	}
	
	public int getCodTipoSanguineo(){
		
		return CodTipoSanguineo;
	}
	
	public void setCodTipoSanguineo(int CodTipoSanguineo){
		
		this.CodTipoSanguineo = CodTipoSanguineo;
	}
	
	public String getDescricaoTipoSanguineo(){
		
		return DescricaoTipoSanguineo;
	}
	
	public void setDescricaoTipoSanguineo(String DescricaoTipoSanguineo){
		
		this.DescricaoTipoSanguineo = DescricaoTipoSanguineo;
	}

}
