package com.app.DAO;

import com.app.model.Hemocentros;
import com.app.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDAO extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME    = "doarSP.db";
	private static final int DATABASE_VERSION 	 = 2;
	
	public final static String TB_USUARIO	     = "TB_Usuario";
	public final static String ID 			  	 = "USU_CodUsuario";
	public final static String LOGIN			 = "USU_Login";
	public final static String SENHA 			 = "USU_Senha";
	public final static String LOGGEDIN          = "USU_LoggedIn";
	
	public final static String TABLE_NAME_POSTO  = "TB_PostosDoacao";
	public final static String IDPOSTO           = "POS_CodPosto";
	public final static String ENDPOSTO          = "POS_EndPosto";
	public final static String TELPOSTO          = "POS_TelPosto";
	public final static String NOMEPOSTO         = "POS_NomePosto";
	public final static String LATITUDE          = "POS_Latitude";
	public final static String LONGITUDE         = "POS_Longitude";		
	
	//Usuario
	private static final String DATABASE_CREATE  = " CREATE TABLE IF NOT EXISTS "
	+ " TB_Usuario ( USU_CodUsuario integer not null primary key, "
	+ "				USU_LoggedIn 	integer not null,"
	+ "				USU_Login 		text not null,"
	+ "				USU_Senha 		text not null"
	+ "			  );";
	
	private String[] allColumns = { AppDAO.ID, AppDAO.LOGGEDIN, AppDAO.LOGIN, AppDAO.SENHA  };
	
	//Postos
	private static final String DATABASE_CREATE_POSTOS = " CREATE TABLE IF NOT EXISTS "
	+ " TB_PostosDoacao ( POS_CodPosto integer not null primary key, "  	
	+ "					 POS_EndPosto text not null,"		
	+ "					 POS_NomePosto text not null,"
	+ "					 POS_Latitude real not null,"
	+ "					 POS_Longitude real not null"	
	+ "					);";
	
	private String[] allColumnsPosto = { AppDAO.IDPOSTO, AppDAO.ENDPOSTO, AppDAO.NOMEPOSTO, 
										 AppDAO.LATITUDE, AppDAO.LONGITUDE };
	
	public AppDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);        
	}	
	
	@Override 
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try{
			db.execSQL(DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE_POSTOS);
			db.setTransactionSuccessful();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			db.endTransaction();
		}
	} 
	@Override 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 	     
	    onCreate(db); 
	}
	
	/** Usuario **/
	
	public boolean userInsert(User userData)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		try
		{			
			ContentValues values = new ContentValues();
			values.put(AppDAO.ID, userData.getCodUsuario());
			values.put(AppDAO.LOGGEDIN, userData.getIsLoggedIn());
			values.put(AppDAO.LOGIN, userData.getLogin());
			values.put(AppDAO.SENHA, userData.getSenha());

			database.insert(AppDAO.TB_USUARIO, null, values);
			return true;
		}
		catch (Exception Ex)
		{
			Ex.printStackTrace();
			return false;
		}
		finally
		{
			database.close();
		}
	}
	
	public boolean CheckIfExistsUser()
	{	
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{
			String[] column = { AppDAO.ID };
			Cursor query = database.query(AppDAO.TB_USUARIO, column, null,
					null, null, null, null);
			if(query.getCount() > 0){
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception Ex)
		{			
			database.close();
			return false;
		}		
	}
	
	public void deleteUser(){
		SQLiteDatabase database = this.getReadableDatabase();
		database.delete(AppDAO.TB_USUARIO, null, null);
		database.close();
	}
	
	public User getUserData(User userData)
	{
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{						
			Cursor query = database.query(AppDAO.TB_USUARIO, allColumns, null,
					null, null, null, null);
			query.moveToFirst();
			userData.setCodUsuario(query.getInt(0));
			userData.setIsLoggedIn(query.getInt(1) == 1 ? true : false);
			userData.setLogin(query.getString(2));
			userData.setSenha(query.getString(3));
		}
		catch(Exception Ex)
		{			
			database.close();				
		}
		return userData;
	}
	
	public void updateUser(User Usuario)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		try
		{			
			ContentValues values = new ContentValues();
			values.put(AppDAO.LOGIN, Usuario.getLogin());
			values.put(AppDAO.SENHA, Usuario.getSenha());
			String whereClause = " " + AppDAO.ID + " = "
					+ Usuario.getCodUsuario();
			
			database.update(AppDAO.TB_USUARIO, values, whereClause, null);
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		finally
		{
			database.close();
		}
	}
	
	/** Fim Usuario **/
	
	/** Postos **/
	
	public void initializePostosInBd(String[][] postosValues)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		try
		{						
			for (int i = 0; i <= postosValues.length; i++)
			{
				values.clear();
				values.put(AppDAO.IDPOSTO, postosValues[i][0]);
				values.put(AppDAO.ENDPOSTO, postosValues[i][1]);
				values.put(AppDAO.NOMEPOSTO, postosValues[i][2]);
				values.put(AppDAO.LATITUDE, postosValues[i][3]);
				values.put(AppDAO.LONGITUDE, postosValues[i][4]);
				database.insert(AppDAO.TABLE_NAME_POSTO, null, values);				
			}										
		}
		catch (Exception Ex)
		{
			Ex.printStackTrace();			
		}
		finally
		{
			database.close();
		}
	}
	
	public boolean checkPostos()
	{	
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{		
			database.delete(AppDAO.TABLE_NAME_POSTO, null, null);
			String[] column = { AppDAO.IDPOSTO };
			Cursor query = database.query(AppDAO.TABLE_NAME_POSTO, column, null,
					null, null, null, null);
			if(query.getCount() > 0){
				return true;
			}
			else{
				return false;
			}
		}
		catch(Exception Ex)
		{			
			database.close();
			return false;			
		}		
	}
	
	public Cursor getAllPostos()
	{
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{						
			Cursor query = database.query(AppDAO.TABLE_NAME_POSTO, allColumnsPosto, null,
					null, null, null, null);
			return query;				
		}
		catch(Exception Ex)
		{						
			database.close();
			return null;
		}			
	}
	
	public Hemocentros getPosto(int id, Hemocentros hemo)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		 
	    try{
	    	Cursor cursor = db.query(AppDAO.TABLE_NAME_POSTO, allColumnsPosto, AppDAO.IDPOSTO + "=?",
		            new String[] { String.valueOf(id) }, null, null, null, null);
		    if (cursor != null){
		        cursor.moveToFirst();
		    	hemo.setEndPosto(cursor.getString(1));
		    	hemo.setNomePosto(cursor.getString(2));
		    }
			db.close();
	    }
	    catch(Exception e){
	    	e.getMessage();
	    }
		
	    return hemo;
	}
	
	/** Fim Postos **/
}
