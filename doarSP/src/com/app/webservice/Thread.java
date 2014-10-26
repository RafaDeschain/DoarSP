package com.app.webservice;

import com.app.doarsp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;

public class Thread extends AsyncTask<String, Void, String> {
	
	AlertDialog alertdialog;
	WebService webservice;
	Activity activity;
	InterfaceListener listener;

	public Thread(Activity activity, WebService webservice, InterfaceListener listener){
		setActivity(activity);
		setWebservice(webservice);
		setListener(listener);
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        setAlertdialog(Utils.showDialog(getActivity(), "Aguarde", "Carregando...", false));
    }

	protected String doInBackground(String... urls) {
		return getWebservice().connectWS();
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		getAlertdialog().dismiss();
		getListener().returningCall(result);
	}
	
	/** Getters and Setters **/
	
	public AlertDialog getAlertdialog() {
		return alertdialog;
	}

	public void setAlertdialog(AlertDialog alertdialog) {
		this.alertdialog = alertdialog;
	}

	public WebService getWebservice() {
		return webservice;
	}

	public void setWebservice(WebService webservice) {
		this.webservice = webservice;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	public InterfaceListener getListener() {
		return listener;
	}

	public void setListener(InterfaceListener listener) {
		this.listener = listener;
	}
	
}