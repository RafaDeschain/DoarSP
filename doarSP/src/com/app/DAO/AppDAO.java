package com.app.DAO;

import java.io.ByteArrayOutputStream;

import com.app.doarsp.R;
import com.app.model.User;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class AppDAO extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME    = "doarSP.db";
	private static final int DATABASE_VERSION 	 = 2;
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
	public final static String LOGGEDIN          = "USU_LoggedIn";
	public final static String TB_USUARIO	     = "TB_Usuario";
	public final static String TABLE_NAME_POSTO  = "TB_PostosDoacao";
	public final static String IDPOSTO           = "POS_CodPosto";
	public final static String ENDPOSTO          = "POS_EndPosto";
	public final static String TELPOSTO          = "POS_TelPosto";
	public final static String NOMEPOSTO         = "POS_NomePosto";
	public final static String LATITUDE          = "POS_Latitude";
	public final static String LONGITUDE         = "POS_Longitude";		
	
	private static final String DATABASE_CREATE  = " CREATE TABLE IF NOT EXISTS "
	+ " TB_Usuario ( USU_CodUsuario integer not null primary key, "  
	+ "				USU_TpSanguineo integer not null,"
	+ "             USU_Nome text not null,"
	+ "				USU_EndEmail text not null,"
	+ " 			USU_NotificacaoPush integer not null,"
	+ "				USU_NotificacaoEmail integer not null,"
	+ "				USU_StatusApto integer not null,"
	+ "				USU_DtdUltimaDoacao text,"
	+ "				USU_DtdNascimento text,"
	+ "				USU_ArqFoto blob,"
	+ "				USU_LoggedIn integer not null"
	+ "			  );";
	
	private static final String DATABASE_CREATE_POSTOS = " CREATE TABLE IF NOT EXISTS "
	+ " TB_PostosDoacao ( POS_CodPosto integer not null primary key, "  	
	+ "					 POS_EndPosto text not null,"		
	+ "					 POS_NomePosto text not null,"
	+ "					 POS_Latitude real not null,"
	+ "					 POS_Longitude real not null"	
	+ "					);";	
	
	private String[] allColumns = { AppDAO.ID, AppDAO.TPSANGUINEO, AppDAO.NOME, AppDAO.EMAIL, AppDAO.NOTIFICAOPUSH, AppDAO.NOTIFICACAOEMAIL, 
			AppDAO.STATUSAPTO, AppDAO.DTDULTIMADOACAO, AppDAO.DTDNASCIMENTO  };	
	
	private String[] allColumnsQuery = { AppDAO.ID, AppDAO.TPSANGUINEO, AppDAO.NOME, AppDAO.EMAIL, AppDAO.NOTIFICAOPUSH, AppDAO.NOTIFICACAOEMAIL, 
			AppDAO.STATUSAPTO, AppDAO.DTDULTIMADOACAO, AppDAO.DTDNASCIMENTO, AppDAO.ARQFOTO, AppDAO.LOGGEDIN  };
	
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
	
	public boolean postUpdate(User Usuario)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		try
		{			
			ContentValues values = new ContentValues();
			values.put(AppDAO.ID, Usuario.getCodUsuario());
			values.put(AppDAO.TPSANGUINEO, Usuario.getTpSanguineo());
			values.put(AppDAO.NOME, Usuario.getNome());
			values.put(AppDAO.EMAIL, Usuario.geteMail());
			values.put(AppDAO.NOTIFICAOPUSH, Usuario.getNotificacaoPush());
			values.put(AppDAO.NOTIFICACAOEMAIL, Usuario.getNotificacaoEmail());
			values.put(AppDAO.STATUSAPTO, Usuario.getStatusApto());
			values.put(AppDAO.DTDULTIMADOACAO, Usuario.getDtdUltimaDoacao());
			values.put(AppDAO.LOGGEDIN, Usuario.getIsLoggedIn());
			String whereClause = " " + AppDAO.ID + " = "
					+ Usuario.getCodUsuario();

			database.update(AppDAO.TB_USUARIO, values, whereClause, null);

			Cursor query = database.query(AppDAO.TB_USUARIO, allColumns,
					whereClause, null, null, null, null);
			convertQueryForUserModel(query, Usuario);
			return true;
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
			return false;
		}
		finally
		{
			database.close();
		}
	}
	
	public boolean CheckPostos()
	{	
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{		
			database.delete(AppDAO.TABLE_NAME_POSTO, null, null);
			String[] column = { AppDAO.IDPOSTO };
			Cursor query = database.query(AppDAO.TABLE_NAME_POSTO, column, null,
					null, null, null, null);
			return query.getCount() > 0;
		}
		catch(Exception Ex)
		{			
			database.close();
			return false;			
		}		
	}		
	
	public boolean CheckIfExistsUser()
	{	
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{		
			// parar sempre criar um usuario - Caso precise depurar o postInsert
			//database.delete(UtilsDAO.TABLE_NAME, null, null);
			String[] column = { AppDAO.ID };
			Cursor query = database.query(AppDAO.TB_USUARIO, column, null,
					null, null, null, null);
			return query.getCount() > 0;
		}
		catch(Exception Ex)
		{			
			database.close();
			return true;			
		}		
	}	
	
	public boolean postInsert(User userData, Resources res)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		try
		{			
			ContentValues values = new ContentValues();
			values.put(AppDAO.ID, userData.getCodUsuario());
			values.put(AppDAO.TPSANGUINEO, userData.getTpSanguineo());
			values.put(AppDAO.NOME, userData.getNome());
			values.put(AppDAO.EMAIL, userData.geteMail());
			values.put(AppDAO.NOTIFICAOPUSH, userData.getNotificacaoPush());
			values.put(AppDAO.NOTIFICACAOEMAIL, userData.getNotificacaoEmail());
			values.put(AppDAO.STATUSAPTO, userData.getStatusApto());
			values.put(AppDAO.DTDULTIMADOACAO, userData.getDtdUltimaDoacao());
			values.put(AppDAO.DTDNASCIMENTO, userData.getDtdNascimento());
			values.put(AppDAO.ARQFOTO, getFirstImageForSave(res));

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
	
	private byte[] getFirstImageForSave(Resources res)
	{
		try
		{		
			Drawable drawable = res.getDrawable(R.drawable.ic_firstachivement);
			Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			byte[] blob = stream.toByteArray();
			return blob;
		}
		catch(Exception Ex)
		{
			return null;
		}		
	}
	
	public void getUserData(User userData)
	{
		SQLiteDatabase database = this.getReadableDatabase();
		try
		{						
			Cursor query = database.query(AppDAO.TB_USUARIO, allColumnsQuery, null,
					null, null, null, null);
			if (query.getCount() > 0)
				convertQueryForUserModel(query, userData);				
		}
		catch(Exception Ex)
		{			
			database.close();					
		}		
	}
	
	private User convertQueryForUserModel(Cursor query, User userData){
		query.moveToFirst();
		if (userData == null)
		{
			userData = new User();
		}
		userData.setCodUsuario(query.getInt(0));
		userData.setTpSanguineo(query.getInt(1));
		userData.setNome(query.getString(2));
		userData.seteMail(query.getString(3));
		userData.setNotificacaoPush(query.getInt(4) == 1 ? true : false );
		userData.setNotificacaoEmail(query.getInt(5) == 1 ? true : false); 
		//userData.setStatusApto(query.getInt(6)); 
		userData.setDtdUltimaDoacao(query.getString(7));
		userData.setDtdNascimento(query.getString(8));
		
		if (query.getColumnCount() == 10)
		{
			userData.setImageAchivement(query.getBlob(9));
		}
		return userData;
	}
	
	public void initializePostosInBd(String[][] postosValues)
	{
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		try
		{						
			for (int i = 0; i <= postosValues.length; i++)
			{
				values.clear();
				values.put(AppDAO.IDPOSTO, i);
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
	
	public Cursor getCursorAllPostos()
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
}
