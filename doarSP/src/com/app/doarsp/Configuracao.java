package com.app.doarsp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Decoder.BASE64Encoder;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import android.app.Activity;

import android.app.AlertDialog;

@SuppressLint("SimpleDateFormat")
public class Configuracao{
	
	DrawerLayout mDrawerLayout;
	ActionBar actionBar;
	AlertDialog alertDialog;
	
	public AlertDialog getAlertDialog() {
		return alertDialog;
	}

	public void setAlertDialog(AlertDialog alertDialog) {
		this.alertDialog = alertDialog;
	}

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
			Configuracao.showMessage(context, msg, 2);
		}
		return flag;
	}
	
	//Método para validar data
	public static boolean validaData(EditText data){
		
		boolean valido = true;
		
		if(isEmpty(data)){
			data.setError("Por favor, coloque uma data");
			return false;
		}
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			Date date = format.parse(data.getText().toString());
			Date now = new Date();								
			Date limite = format.parse("01/01/1900");
			if (date.after(now) || date.before(limite))
			{
				data.setError("Por favor, escolha data válida");
				valido = false;
			}
		} catch (java.text.ParseException e) {				
			e.printStackTrace();
		}
		return valido;
	}
	
	//Método para validar email
	public static boolean validaEmail(EditText email){
		
		if(isEmpty(email)){
			email.setError("Por favor, coloque um email");
			return false;
		}
		
		if (!email.getText().toString().contains("@")){
			email.setError("Por favor, coloque um email válido");
			return false;
		}
		return true;
	}
    
	//Desabilita o slide do menu lateral	
	public static void disableSlideMenu(DrawerLayout mDrawerLayout, ActionBar actionBar){
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setHomeButtonEnabled(false);
	}
	
	//Habilita o slide do menu lateral
	public static void enableSlideMenu(DrawerLayout mDrawerLayout, ActionBar actionBar){
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}
	
	//Criptografa senha
	public static String encripta(String senha) {     
        try {     
             MessageDigest digest = MessageDigest.getInstance("MD5");      
             digest.update(senha.getBytes());      
             BASE64Encoder encoder = new BASE64Encoder();      
             return encoder.encode (digest.digest ());      
        } catch (NoSuchAlgorithmException ns) {     
             ns.printStackTrace();      
             return senha;      
        }      
   }
	
	//Métodos para mascara de data
	public static String unmask(String s) {
		return s.replaceAll("[.]", "").replaceAll("[-]", "")
				.replaceAll("[/]", "").replaceAll("[(]", "")
				.replaceAll("[)]", "");
	}
	
	//Métodos para mascara de data
	public static TextWatcher insert(final String mask, final EditText ediTxt) {
		return new TextWatcher() {
			boolean isUpdating;
			String old = "";
 
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String str = Configuracao.unmask(s.toString());
				String mascara = "";
				if (isUpdating) {
					old = str;
					isUpdating = false;
					return;
				}
				int i = 0;
				for (char m : mask.toCharArray()) {
					if (m != '#' && str.length() > old.length()) {
						mascara += m;
						continue;
					}
					try {
						mascara += str.charAt(i);
					} catch (Exception e) {
						break;
					}
					i++;
				}
				isUpdating = true;
				ediTxt.setText(mascara);
				ediTxt.setSelection(mascara.length());
			}
 
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
 
			public void afterTextChanged(Editable s) {
			}
		};
	}
	
	//Método para trocar o Fragment
	public static void trocarFragment(Fragment fragment, FragmentManager fragmentManager, boolean backStack){
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.frame_container, fragment);
		if(backStack == true){
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
	}
	
	//Método que verifica se EditText é nulo
	public static boolean isEmpty(EditText etText){
		return etText.getText().toString().trim().length() == 0;
	}
	
	// Sobrecarga do método para escolher qual view trocar
	public static void trocarFragment(Fragment fragment, FragmentManager fragmentManager, int idView){
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(idView, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	//Método para esconder teclado
	public static void hideKeyboard(Activity act){
		InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm.isActive()){
			imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
		}
	}
	
	//Mostrar caixa de dialogo
	public static AlertDialog showDialog(Activity act, CharSequence title, CharSequence message, boolean cancelable){
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(act).create();
		alertDialog.setTitle(title);
		alertDialog.setCancelable(cancelable);
		alertDialog.setMessage(message);
		alertDialog.show();
		return alertDialog;
	}
}
