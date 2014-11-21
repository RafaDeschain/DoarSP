package com.app.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceReceiver extends BroadcastReceiver{
	
	@Override
    public void onReceive(Context context, Intent intent)
    {
		Intent service1 = new Intent(context, UpdateLocation.class);
		context.startService(service1);
    }  
}
