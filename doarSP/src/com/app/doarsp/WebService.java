package com.app.doarsp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.os.AsyncTask;

@SuppressWarnings("unused")
public class WebService{
	

	private final String NAMESPACE = "http://192.168.0.14/doarsp/";
	private final String URL = "http://192.168.0.14/doarsp/doarsp.asmx/";
	private String SOAP_CALL;
	
	private String METHOD_NAME;
	private String[][] params;
	private String wsReturn;
	
	public String getWsReturn() {
		return wsReturn;
	}

	public void setWsReturn(String wsReturn) {
		this.wsReturn = wsReturn;
	}

	Context context;
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
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
	
	public String callWebService(String METHOD_NAME, String[][] params) {
		
		setMETHOD_NAME(METHOD_NAME);
		setParams(params);
		
		CallWebService call = new CallWebService();
		call.execute();
		
		return getWsReturn();
	}
	
	private class CallWebService extends AsyncTask<String, String, String> {
		
		private String resp;
		
		@Override
		protected String doInBackground(String... params) {
			
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
				setWsReturn(response.toString());
				return "Sucesso";
				
			}
			catch(Exception ex)
			{			
				return "Erro #001, por favor tente mais tarde.";
			}
			
		}
		
		@Override
		protected void onPreExecute() {
		 // Things to be done before execution of long running operation. For
		 // example showing ProgessDialog
		}
		
		@Override
		protected void onProgressUpdate(String... text) {
			// Things to be done while execution of long running operation is in
			// progress. For example updating ProgessDialog
		}
		
		@Override
		protected void onPostExecute(String result) {
			// execution of result of Long time consuming operation
			Utils.showMessage(getContext(), result, 3);
	  	}
	}
}