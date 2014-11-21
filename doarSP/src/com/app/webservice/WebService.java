package com.app.webservice;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;	

import com.app.gcm.AppGCM;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

@SuppressWarnings("unused")
public class WebService{
	
	private final String SOAP_NAMESPACE = "http://doarsp.ws/";
	//private final String SOAP_URL = "http://192.168.0.14/doarsp/doarsp.asmx"; Casadei
	private final String SOAP_URL = "http://doarsp.ddns.net:8080/doarsp/doarsp.asmx"; // Palazolo
	private String SOAP_METHOD_NAME;
	
	private SoapObject request;
	
	private String[][] params;
	private String wsReturn;
	private String resp;
	
	/** GCM **/
	AppGCM gcm;
	String regId;
	Activity activity;
	
	public WebService(String method, String[][] params, Activity activity){
		setMETHOD_NAME(method);
		setParams(params);
		this.activity = activity;
	}
	
	public WebService(String method, String[][] params){
		setMETHOD_NAME(method);
		setParams(params);
	}
	
	public String connectWS(){
		
		request = new SoapObject(SOAP_NAMESPACE, SOAP_METHOD_NAME);
		
		/** Caso seja registro, cria o GCM do usuário **/
		
		if(getMETHOD_NAME() == "usuario_insereNovoUsuario"){
           gcm = new AppGCM(activity);
           regId = gcm.registrarGCM();
           
           if(!regId.contains("erro") || !regId.isEmpty()){
        	   	PropertyInfo pi = new PropertyInfo();
	   			pi.setName("gcmId");
	   			pi.setValue(regId);
	   			pi.setType(String.class);
	   			request.addProperty(pi);
           }
		}		
		
		/** Fim GCM **/
		
		String[][] prop = getParams();
		
		for (int i = 0; i < params.length; i++) {
			PropertyInfo pi = new PropertyInfo();
			pi.setName(prop[i][0]);
			pi.setValue(prop[i][1]); //get the string that is to be sent to the web service
			pi.setType(String.class);
			request.addProperty(pi);
		}
		 
		SoapSerializationEnvelope envp = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envp.dotNet = true;
		envp.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_URL, 60000);
		
		try {
			String SOAP_ACTION = SOAP_NAMESPACE + SOAP_METHOD_NAME;
			androidHttpTransport.call(SOAP_ACTION, envp);
			SoapPrimitive response = (SoapPrimitive) envp.getResponse();
			resp = response.toString();
			setWsReturn(resp);
		} catch (Exception e) {
			setWsReturn("Erro");
			Log.i("WS Error->",e.toString());
		}
		
		return getWsReturn();
	}
	
	/** Getters and Setters **/
	
	public String getWsReturn() {
		return wsReturn;
	}

	public void setWsReturn(String wsReturn) {
		this.wsReturn = wsReturn;
	}

	public String getMETHOD_NAME() {
		return SOAP_METHOD_NAME;
	}

	public void setMETHOD_NAME(String SOAP_METHOD_NAME) {
		this.SOAP_METHOD_NAME = SOAP_METHOD_NAME;
	}

	public String[][] getParams() {
		return params;
	}

	public void setParams(String[][] params) {
		this.params = params;
	}
}