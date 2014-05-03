package com.app.DAO;

import com.app.model.UserModel;
import android.content.Context;
import android.content.res.Resources;

public class ApapterDAO {
	private UtilsDAO Utils;
	
	public ApapterDAO(Context context){
		Utils = new UtilsDAO(context);
	}
	
	public boolean postUpdate(UserModel Usuario)
	{
		return Utils.postUpdate(Usuario);
	}
	
	public boolean CheckIfExistsUser()
	{					
		return Utils.CheckIfExistsUser();
	}			
	
	public boolean posInsert(UserModel userData, Resources res)
	{
		return Utils.postInsert(userData, res);
	}
	
	public void getUserData(UserModel userData)
	{
		Utils.getUserData(userData);		
	}
}
