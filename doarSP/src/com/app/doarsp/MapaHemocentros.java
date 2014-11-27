package com.app.doarsp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.doarsp.R;
import com.app.model.Hemocentros;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaHemocentros extends Fragment{		
	
	private GoogleMap map;
	private Context context;
	protected LocationManager locationManager;
	
	public static View rootView;
	
	// GPS status
    boolean isGPSEnabled = false;
 
    // Internet status
    boolean isNetworkEnabled = false;
    
	public void onDestroyView() {
		// Necessario destruir o mapa se não dá vazamento de memória e erro ao dar inflate novamente.
		Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
        super.onDestroyView();
    }		

	@SuppressWarnings("unused")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_hemocentro, container, false);
    	
		//Pega a instanciação do mapa.
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();							    
		context = rootView.getContext();			
		
		//Instancia o objeto que faz o retorno dos postos.
		Hemocentros postos = new Hemocentros(rootView.getContext());
		
		//Query que vai retornar os postos que devem ser iterados nas marcações.
		Cursor query = postos.getAllPostos();
		query.moveToFirst();
		
		for (int i = 0; i < query.getCount(); i ++)
		{
			LatLng hemocentro = new LatLng(query.getDouble(3), query.getDouble(4));
			
			Marker postoDoacao = map.addMarker(new MarkerOptions()
			  	.position(hemocentro)
				.title(query.getString(2))
				.snippet(query.getString(1))
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_postos_map)));
			query.moveToNext();						
		}
		
		//Cria o Location Manager
		getActivity().getApplicationContext();
		locationManager = (LocationManager) getActivity().getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		
		//Verifica o status do GPS
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        //Verifica o status do 3G
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        
        //Verifica o status do WIFI
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
	    //Verifica se a Internet e o GPS estão habilitados
		if(isGPSEnabled && (isNetworkEnabled || wifiInfo.isAvailable())){
			map.setMyLocationEnabled(true);
		}
		else if(!isGPSEnabled)
		{
			Configuracao.showDialog(getActivity(), "Ops..", "Ative o seu GPS para localizar os hemocentros", true);
			Principal principal = new Principal();
			Configuracao.trocarFragment(principal, getFragmentManager(), false);
		}
		else{
			Configuracao.showDialog(getActivity(), "Ops..", "Ative sua internet para localizar os hemocentros", true);
			Principal principal = new Principal();
			Configuracao.trocarFragment(principal, getFragmentManager(), false);
		}
		
	    return rootView;
		
		// TODO: Necessário montar uma classe que implemente uma locationclient para pegar as localizações para os mapas, 
		// estou vendo como fazer de forma coerente para que fique global

		// animação ao mover a camera
		//LatLng myPosition = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
		//map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));		
		//map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);		
		
	}
}