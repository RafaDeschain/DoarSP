package com.app.gcm;

import android.content.Context;
import android.content.Intent;

public class UtilitariosComum {
	
	  String SERVER_URL = "http://my-host/GCM-server.asmx/PostRegistration-Id";
	 
	 // api do projeto no google
	  String senderID = "";
	  
	 // tag usada para log
	  String tag = "teste";
	  
	 // String para enviar mensagem.  
	  String Display_Messge_action = " com.app.doarsp.DISPLAY_MESSAGE"; 
	  
	  // mensagem a ser exibida
	  String extra_message = "message";
	  
	  	  void displayMessage(Context context, String message) {
		  Intent intent = new Intent(Display_Messge_action);
		  intent.putExtra(Display_Messge_action, message);
		  context.sendBroadcast(intent);
		 }
		
	  
	  

	  
	

}
