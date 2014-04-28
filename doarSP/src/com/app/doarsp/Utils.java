package com.app.doarsp;

import android.content.Context;
import android.widget.Toast;

public class Utils {
	
	public static void showMessage(Context context, String texto, int duracao)
	{
		Toast.makeText(context, texto, (duracao > 0 ? duracao : Toast.LENGTH_SHORT)).show();				
	}
}
