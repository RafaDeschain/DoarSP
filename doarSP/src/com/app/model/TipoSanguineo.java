package com.app.model;

import android.content.Context;

public class TipoSanguineo {
	
	private int CodTipoSanguineo;
	private String DescricaoTipoSanguineo;
	
	public TipoSanguineo(Context context){
		
		
	}
	
	public TipoSanguineo(){
		
		
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
