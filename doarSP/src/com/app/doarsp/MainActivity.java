package com.app.doarsp;

import com.app.adapter.NavDrawerListAdapter;
import com.app.model.Hemocentros;
import com.app.model.User;
import com.app.services.ServiceReceiver;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	//Chama a classe util
	Configuracao util;
	
	//Cria o DrawerLayout
	private DrawerLayout mDrawerLayout;
	
	//Cria a ListView do menu do DrawerLayout
	private ListView mDrawerList;
	
	//Cria o ActionBar do ListView
	private ActionBarDrawerToggle mDrawerToggle;
	
	//Cria o titulo que fica na barra superior
	private CharSequence mDrawerTitle;

    //T�tulo do app
	private CharSequence mTitle;

	//Itens do menu
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	//Servi�o
	private PendingIntent pendingIntent;
	
	public Activity activity;
	
	//Modelo
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle 	= getTitle();
		navMenuTitles 			= getResources().getStringArray(R.array.nav_drawer_items);		
		navMenuIcons 			= getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout 			= (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList 			= (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems 			= new ArrayList<NavDrawerItem>();
		
		/** Adiciona os itens no menu lateral **/

		//Para adicionar contador ap�s o nome: true, "15"
		// Principal
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Buscar Doa��es
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Abrir Solicita��o
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Mural
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Informa��es
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// Ranking
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		
		navMenuIcons.recycle();
		
		/** Adiciona os itens no menu lateral - Fim**/

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
	
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		Hemocentros hemocentrosInsert = new Hemocentros(getApplicationContext());
		
		// Cria��o b�sica dos postos, update v�o ser feitos atrav�s do servidor
		if (!hemocentrosInsert.checkPosto())
		{
			hemocentrosInsert.initializeValuesInBd();
		}
		
		/**
		 * Cria o servi�o que atualiza a localiza��o do usu�rio no bd
		 */
		
        Intent alarmIntent = new Intent(MainActivity.this, ServiceReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
	    
        //O intervalo de chamada do servi�o est� setado de 10 em 10 minutos
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 10;
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
		
	    /**
	     * Fim do servi�o
	     */
		
		//Vai para a tela de login, caso ele ja esteja logado, a propria classe ja faz o tratamento.
		Fragment login = new Login();
		Configuracao.trocarFragment(login, getFragmentManager(), false);
		activity = this;
	}

	/**
	 * Slide menu item click listener
	 * */
	
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) { 
			displayView(position);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		menu.findItem(R.id.menuSair).setVisible(false);
		return true;
	}
	
	/** Listener dos bot�es do ActionBar **/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.menuAbout:
			menuSobre();
			return true;
		case R.id.menuSair:
			menuSair();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	//Chama a tela de informa��es ao clicar em "Sobre" no ActionBar
	public void menuSobre(){
		Fragment about = new Informacoes();
		Configuracao.hideKeyboard((Activity) this);
		Configuracao.trocarFragment(about, getFragmentManager(), true);
	}
	
	//Destroi a sess�o logada do usu�rio
	public void menuSair(){
		user.deleteUser();
		Fragment login = new Login();
		Configuracao.trocarFragment(login, getFragmentManager(), false);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menuAbout).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	
	private void displayView(int position) {
		
		//Pega qual � o item que est� sendo clicado
		Fragment fragment = null;
		
		switch (position) {
		case 0:
			fragment = new Principal();
			break;
		case 1:
			fragment = new BuscarDoacoes();
			break;
		case 2:
			fragment = new NovaSolicitacao();
			break;
		case 3:
			fragment = new NovoMural();
			break;
		case 4:
			fragment = new AlterarDados();
			break;			
		case 5:
			fragment = new ConsultarRanking();
			break;

					
		default:
			break;
		}
		
		//seta o fragment para qual foi clicado
		if (fragment != null) {
			
			Configuracao.trocarFragment(fragment, getFragmentManager(), true);
			
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	/** Getters and Setters **/
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	/** Getters and Setters end **/
}