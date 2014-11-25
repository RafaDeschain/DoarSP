package com.app.gcm;

import java.util.concurrent.atomic.AtomicInteger;

import com.app.doarsp.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

import java.io.IOException;

public class AppGCM {
	
	public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    
    private final String SENDER_ID = "595477754580";
    
    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    SharedPreferences prefs;
    String regid;
    
    /**
     * Tag utilizada nas mensagens de log.
     */
    static final String TAG = "DoarSP";
	
    Activity activity;
	Context  context;
	
	public AppGCM(Activity activity){
		this.activity = activity;
		context = activity.getApplicationContext();
	}
	
	public AppGCM(Context context){
		this.context = context;
	}
	
	/** Método principal **/
	
	public String registrarGCM(){
		
		String msg = "";
		
		if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(context);
            regid = getRegistrationId(context);
            
            if (regid.isEmpty()) {
                msg = registerInBackground();
            }
            else{
            	msg = regid;
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
            msg = "erro";
        }
		return msg;
	}
	
	/**
	 * Pega o ID de registro da aplicação no serviço GCM.
	 * Caso retorne em branco, será necessario registrar.
	 */
	
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Verifica se o aplicativo foi atualizado, se sim, deve limpar o registro.
	    // Desde que o regID existente não seja compativel com a nova versão do App
	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	/**
	 * Retorna o SharedPreferences
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // Pega o ID que está gravado nas preferencias
	    return context.getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	/**
	 * Retorna a versão do aplicativo
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * Verifica se o dispositivo possui o Google Play Services APK.
	 * Caso não possua, mostra um dialogo para ele baixar o APK.
	 */
	
	private boolean checkPlayServices() {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
	                    PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i(TAG, "This device is not supported.");
	        }
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Registra o ID do dispositivo no GCM
	 */
	private String registerInBackground() {
	    String msg = "";
	    try {
	        if (gcm == null) {
	            gcm = GoogleCloudMessaging.getInstance(context);
	        }
	        regid = gcm.register(SENDER_ID);
	        msg = regid;
	        
	        // Grava o ID de registro para não precisar registrar novamente
	        storeRegistrationId(context, regid);
	    } catch (IOException ex) {
	        msg = "erro :" + ex.getMessage();
	    }
	    return msg;
	}
	
	/**
	 * Remove o registro ID do dispositivo no GCM
	 */
	public void unRegister(){
		try {
	        if (gcm == null) {
	            gcm = GoogleCloudMessaging.getInstance(context);
	        }
	        
	        gcm.unregister();
	        
	    } catch (IOException ex) {
	        ex.getMessage();
	    }
	}
	
	/**
	 * Grava o ID de registro e a versão do app na aplicação
	 */
	
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    editor.commit();
	}
}
