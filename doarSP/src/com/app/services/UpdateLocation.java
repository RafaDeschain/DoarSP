package com.app.services;

import com.app.model.User;
import com.app.webservice.WebService;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

public class UpdateLocation extends Service{
    
	LocationService location;
	
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
		
		if(checarSessao()){
			
			location = new LocationService(getApplicationContext());
			
			if(location != null){
				User user = new User(getApplicationContext());
				user.getUserData();
				
				SendRequest send = new SendRequest(user.getCodUsuario(), location.getLatitude(),location.getLongitude());
				send.execute();
			}
		}
	}
	
	public boolean checarSessao(){
		User user = new User(getApplicationContext());
		return user.CheckIfExistsUser();
	}
	
	private class SendRequest extends AsyncTask<String, Void, Void>{
		
		private String[][] wsparams;
		private int userId;
		private double latitude, longitude;
		private WebService webservice;
		
		public SendRequest(int userId, double latitude, double longitude){
			this.userId = userId;
			this.latitude = latitude;
			this.longitude = longitude;
		}
		
		@Override
		protected Void doInBackground(String... params) {
			
			wsparams = new String[3][2];
			
			wsparams[0][0] = "CodUser";
			wsparams[0][1] = String.valueOf(userId);
			wsparams[1][0] = "latitude";
			wsparams[1][1] = String.valueOf(latitude);
			wsparams[2][0] = "longitude";
			wsparams[2][1] = String.valueOf(longitude);
			
			webservice = new WebService("usuario_AtualizaLocalizacao", wsparams);
			webservice.connectWS();
			
			return null;
		}
	}
}
