package com.app.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UtilsDAO extends SQLiteOpenHelper{
	private static final String DATABASE_NAME    = "doarSP.db";
	private static final int DATABASE_VERSION 	 = 1;
	public final static  String ID 			  	 = "USU_CodUsuario";
	public final static String TPSANGUINEO    	 = "USU_TpSanguineo";
	public final static String NOME           	 = "USU_Nome";
	public final static String EMAIL          	 = "USU_EndEmail";
	public final static String NOTIFICAOPUSH     = "USU_NotificacaoPush";
	public final static String NOTIFICACAOEMAIL  = "USU_NotificacaoEmail";
	public final static String STATUSAPTO        = "USU_StatusApto";
	public final static String DTDULTIMADOACAO   = "USU_DtdUltimaDoacao";
	public final static String DTDNASCIMENTO     = "USU_DtdNascimento";
	public final static String ARQFOTO           = "USU_ArqFoto";
	public final static String TABLE_NAME	     = "TB_Usuario";
	private static final String DATABASE_CREATE  = " CREATE TABLE IF NOT EXISTS "
	+ "TB_Usuario ( USU_CodUsuario integer not null primary key "  
	+ "				USU_TpSanguineo integer not null"
	+ "             USU_Nome text not null"
	+ "				USU_EndEmail text not null"
	+ " 			USU_NotificacaoPush integer not null"
	+ "				USU_NotificacaoEmail integer not null"
	+ "				USU_StatusApto integer not null"
	+ "				USU_DtdUltimaDoacao real"
	+ "				USU_DtdNascimento real"
	+ "				USU_ArqFoto blob"	
	+ ");";
	
	public UtilsDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);        
	}	
	
	@Override 
	public void onCreate(SQLiteDatabase db) { 
	    db.execSQL(DATABASE_CREATE); 
	} 
	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 	     
	    onCreate(db); 
	}		
}
