package com.app.doarsp;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

@SuppressWarnings("unused")
public class WebService {

	private final String NAMESPACE = "http://tempuri.org/";
	private final String URL = "http://10.0.2.2:80/WS.asmx";

	public String callWebService(String METHOD_NAME,
			String[][] params) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		for (int i = 0; i <= params.length; i++) {
			request.addProperty(params[i][0], params[i][1]);
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		
		try
		{
			androidHttpTransport.call(NAMESPACE+METHOD_NAME, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();            
            return response.toString();
			 
		}
		catch(Exception ex)
		{			
			return "Erro #001, por favor tente mais tarde.";
		}	
	}
}