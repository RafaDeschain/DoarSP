package com.app.doarsp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

public class Utils {
	
	public static void showMessage(Context context, String texto, int duracao)
	{
		Toast.makeText(context, texto, (duracao > 0 ? duracao : Toast.LENGTH_SHORT)).show();				
	}
	@SuppressLint("SimpleDateFormat")
	public static boolean validadeValues(Context context, String dateNasc, String eMail)
	{
		boolean flag = true;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String msg = "";
		
		try
		{
			Date date = format.parse(dateNasc);
			Date now = new Date();								
			Date limite = format.parse("01/01/1900");
			if (date.after(now) || date.before(limite))
			{
				msg = "Data inválida";					
				flag = false;
			}
		} catch (java.text.ParseException e) {				
			e.printStackTrace();
		}
		
		if (!eMail.contains("@")){
			if (msg != "")
				msg += "\n"; 					
			msg += "E-mail inválido"; 
			flag = false;
		}
		
		if (!flag){
			Utils.showMessage(context, msg, 2);
		}
		return flag;
	}
	
	public static double getMyPositionLatitude(LocationManager locationManager)
	{
		
		Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);			
		return location.getLatitude();
	}
	
	public static double getMyPositionLongitude(LocationManager locationManager)
	{
		Criteria criteria = new Criteria();
	    String provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);		
		return location.getLongitude();
	}	
}
