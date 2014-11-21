package com.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UpdateLocation extends Service{
        
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
    public void onCreate(){ 
       super.onCreate();
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
	}
}
