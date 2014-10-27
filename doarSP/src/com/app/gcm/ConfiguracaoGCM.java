package com.app.gcm;

import android.content.Context;
import android.content.Intent;

public class ConfiguracaoGCM {

String SERVER_URL = "http://my-host/GCM-server.asmx/PostRegistration-Id";
String senderID = ""; // api do projeto no google
String tag = "teste";  // tag usada para log
String Display_Messge_action = " com.app.doarsp.DISPLAY_MESSAGE";   // String para enviar mensagem.  
String extra_message = "message"; // mensagem a ser exibida
 
	void displayMessage(Context context, String message) {
		Intent intent = new Intent(Display_Messge_action);
		intent.putExtra(Display_Messge_action, message);
		context.sendBroadcast(intent);
	}
}
