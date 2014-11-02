package com.app.DAO;

import com.app.model.User;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;

public class ApapterDAO {
	private AppDAO Utils;
	
	public ApapterDAO(Context context){
		Utils = new AppDAO(context);
	}
	
	public boolean postUpdate(User Usuario)
	{
		return Utils.postUpdate(Usuario);
	}
	
	public boolean CheckIfExistsUser()
	{					
		return Utils.CheckIfExistsUser();
	}			
	
	public boolean posInsert(User userData, Resources res)
	{
		return Utils.postInsert(userData, res);
	}
	
	public void getUserData(User userData)
	{
		Utils.getUserData(userData);		
	}
	
	public Boolean checkPostos()
	{
		return Utils.CheckPostos();
	}
	
	public void initializeValuesInBd(String[][] postosValues)
	{
		Utils.initializePostosInBd(postosValues);
	}
	
	public Cursor getAllPostos()
	{
		return Utils.getCursorAllPostos();
	}
}
