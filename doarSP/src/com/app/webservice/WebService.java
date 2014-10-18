package com.app.webservice;

import java.util.concurrent.ExecutionException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

@SuppressWarnings("unused")
public class WebService{

	private final String NAMESPACE = "http://192.168.0.14/doarsp/";
	private final String URL = "http://192.168.0.14/doarsp/doarsp.asmx/";
	private String SOAP_CALL;
	
	private String METHOD_NAME;
	private String[][] params;
	private String wsReturn;
	private String resp;
	
	public WebService(){
		
	}
	
	public WebService(String method, String[][] params){
		setMETHOD_NAME(method);
		setParams(params);
	}
	
	public String connectWS(){
		
		String[][] prop = getParams();
		
		SoapObject request = new SoapObject(NAMESPACE, getMETHOD_NAME());

		for (int i = 0; i < params.length; i++) {
			request.addProperty(prop[i][0], prop[i][1]);
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try
		{
			SOAP_CALL = URL + METHOD_NAME;
			androidHttpTransport.call(SOAP_CALL, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			resp = response.toString();
		}
		catch(Exception ex)
		{			
			resp = "Erro" ;
		}
		setWsReturn(resp);
		
		return "true";
	}
	
	/** Getters and Setters **/
	
	public String getWsReturn() {
		return wsReturn;
	}

	public void setWsReturn(String wsReturn) {
		this.wsReturn = wsReturn;
	}

	public String getSOAP_CALL() {
		return SOAP_CALL;
	}

	public void setSOAP_CALL(String sOAP_CALL) {
		SOAP_CALL = sOAP_CALL;
	}

	public String getMETHOD_NAME() {
		return METHOD_NAME;
	}

	public void setMETHOD_NAME(String METHOD_NAME) {
		this.METHOD_NAME = METHOD_NAME;
	}

	public String[][] getParams() {
		return params;
	}

	public void setParams(String[][] params) {
		this.params = params;
	}
}