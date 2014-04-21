package com.app.DAO;

import com.app.model.UserModel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ApapterDAO {
	private SQLiteDatabase database;
	private UtilsDAO Utils;
	private String[] allColumns = { UtilsDAO.ID, UtilsDAO.TPSANGUINEO, UtilsDAO.NOME, UtilsDAO.EMAIL, UtilsDAO.NOTIFICAOPUSH, UtilsDAO.NOTIFICACAOEMAIL, 
									UtilsDAO.STATUSAPTO, UtilsDAO.DTDULTIMADOACAO, UtilsDAO.DTDNASCIMENTO  };
	
	public ApapterDAO(Context context){
		Utils = new UtilsDAO(context);
	}
	
	public UserModel postValues(UserModel Usuario)
	{
		ContentValues values = new ContentValues();		
		values.put(UtilsDAO.ID, Usuario.getCodUsuario());
		values.put(UtilsDAO.TPSANGUINEO, Usuario.getTpSanguineo());
		values.put(UtilsDAO.NOME, Usuario.getNome());
		values.put(UtilsDAO.EMAIL, Usuario.geteMail());
		values.put(UtilsDAO.NOTIFICAOPUSH, Usuario.getNotificacaoPush());
		values.put(UtilsDAO.NOTIFICACAOEMAIL, Usuario.getNotificacaoEmail());
		values.put(UtilsDAO.STATUSAPTO, Usuario.getStatusApto());
		values.put(UtilsDAO.DTDULTIMADOACAO, Usuario.getDtdUltimaDoacao());		
		String whereClause = " " + UtilsDAO.ID + " = " + Usuario.getCodUsuario();
		
		database.update(UtilsDAO.TABLE_NAME, values, whereClause, null);
		
		Cursor query = database.query(UtilsDAO.TABLE_NAME, allColumns, whereClause, null, null, null, null);
		return convertQueryForUserModel(query);
	}
	
	private UserModel convertQueryForUserModel(Cursor query){
		query.moveToFirst();
		UserModel UserData = new UserModel();
		UserData.setCodUsuario(query.getInt(0));
		UserData.setTpSanguineo(query.getInt(1));
		UserData.setNome(query.getString(2));
		UserData.seteMail(query.getString(3));
		UserData.setNotificacaoPush(query.getInt(4));
		UserData.setNotificacaoEmail(query.getInt(5)); 
		UserData.setStatusApto(query.getInt(6)); 
		UserData.setDtdUltimaDoacao(query.getString(7));
		UserData.setDtdNascimento(query.getString(8));
		return UserData;
	}
	
}
