package com.app.doarsp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.doarsp.R;
import com.app.model.HemocentrosModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Hemocentros extends Fragment {		
	private GoogleMap map;
	
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
		View rootView = inflater.inflate(R.layout.fragment_hemocentro, null, false);		
		
		// Pega a instanciação do mapa.
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();				
		
	    // Pega o locationManager que vai ser usado para descobrir a latitude e longitude, usando o melhor provider.
		LocationManager objGPS = (LocationManager) rootView.getContext().getSystemService(Context.LOCATION_SERVICE);	    		
		LatLng myPosition = new LatLng(Utils.getMyPositionLatitude(objGPS), Utils.getMyPositionLongitude(objGPS));					
		
		// Marcar com a posição atual.
		Marker myPositionGPS = map.addMarker(new MarkerOptions().position(myPosition).title("Estou aqui"));		
		
		// Instancia o objeto que faz o retorno dos postos.
		HemocentrosModel postos = new HemocentrosModel(rootView.getContext());
		
		// Query que vai retornar os postos que devem ser iterados nas marcações.
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
						.fromResource(R.drawable.ic_whats_hot)));
			query.moveToNext();			
		}
		// move a camera para a posição corrente e zoom de 15
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
		// animação ao mover a camera
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

		return rootView;
	}
}