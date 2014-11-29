package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.doarsp.R;
import com.app.model.Hemocentros;
import com.app.model.Solicitacoes;
import com.app.webservice.InterfaceListener;
import com.app.webservice.Thread;
import com.app.webservice.WebService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaHemocentros extends Fragment implements InterfaceListener{		
	
	/** Mapa **/
	private GoogleMap map;
	private Context context;
	protected LocationManager locationManager;
	
	public static View rootView;
	
	/** Localização **/
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    //Intent que pega o ID do hemocentro baseado no ID do Marker
    Intent intent;
    
    /** Webservice **/
	private WebService webservice;
	private Thread thread;
	private String[][] params;
	
	/** Solicitacoes **/
	public List<Solicitacoes> listaSol;
	private int idHemo;
	
	public void onCreate(Bundle savedInstanceState) {
	    setRetainInstance(true); 
	    super.onCreate(savedInstanceState);     
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if(rootView == null){
			rootView = inflater.inflate(R.layout.fragment_hemocentro, container, false);
	    	
			//Pega a instanciação do mapa.
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();						    
			context = rootView.getContext();
			
			//Instancia o objeto que faz o retorno dos postos.
			Hemocentros postos = new Hemocentros(rootView.getContext());
			
			//Query que vai retornar os postos que devem ser iterados nas marcações.
			Cursor query = postos.getAllPostos();
			query.moveToFirst();
			
			intent = new Intent();
			
			for (int i = 0; i < query.getCount(); i ++)
			{
				LatLng hemocentro = new LatLng(query.getDouble(3), query.getDouble(4));
				
				Marker postoDoacao = map.addMarker(new MarkerOptions()
				  	.position(hemocentro)
					.title(query.getString(2))
					.snippet(query.getString(1))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_postos_map)));
				
				intent.putExtra(postoDoacao.getId(), query.getInt(0));
				
				query.moveToNext();
				
			}
			
			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker arg0) {
					idHemo = intent.getExtras().getInt(arg0.getId());
					
					if(idHemo != -1){
						
						params = new String[1][2];
						params[0][0] = "idHemocentro";
						params[0][1] = String.valueOf(idHemo);
						
						webservice = new WebService("hemocentros_getSolicitacoesHemocentro", params);
						thread = new Thread(getActivity(), webservice, getInterface());
						thread.execute();
					}
				}
			});
			
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
			
			// TODO: Necessário montar uma classe que implemente uma locationclient para pegar as localizações para os mapas, 
			// estou vendo como fazer de forma coerente para que fique global
	
		    // animação ao mover a camera
		    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    
		    if(location != null){
		    	LatLng myPosition = new LatLng(location.getLatitude(), location.getLongitude());
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));		
				map.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
		    }
		}
	    
	    return rootView;
		
	}
	
	/** Returning Call **/
	@Override
	public void returningCall(String solicitacoes) {
		
		try
		{
			listaSol = new ArrayList<Solicitacoes>();
			
			if(solicitacoes.length() > 3){
				JSONArray jsonarray = new JSONArray(solicitacoes);
				
				for(int i = 0; i < jsonarray.length(); i++){
					
					JSONObject json = jsonarray.getJSONObject(i);
					
					listaSol.add(criarSolicitacao(json.getInt("codDoacao"), 
							 json.getInt("idUserSolicitante"), 
							 json.getInt("qtnDoacoes"), 
							 json.getInt("qtnRealizadas"), 
							 json.getInt("hemoCentro"), 
							 json.getInt("tpSanguineo"), 
							 1,
							 json.getString("nomePaciente"), 
							 json.getString("dataAbertura"),
							 json.getString("comentario")));
				}
				
				ListaSolicitacoes sol = new ListaSolicitacoes(listaSol);
				Configuracao.trocarFragment(sol, getFragmentManager(), true);
			}
			else{
				Configuracao.showDialog(getActivity(), "DoarSP", "Não há nenhuma solicitação aberta neste hemocentro", true);
			}
			
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	
	/** Criar solicitações **/
	
	public Solicitacoes criarSolicitacao(int idSolicitacao, int idUsuario,
			int quantidadeSolicitacoes, int quantidadesRealizadas,
			int idHemocentro, int tipoSanguineo, int status, String nome,
			String data, String comentario){
		
		Solicitacoes sol = new Solicitacoes(idSolicitacao, idUsuario, quantidadeSolicitacoes, 
				quantidadesRealizadas, idHemocentro, tipoSanguineo, status, nome, data, comentario);
		
		return sol;
	}
	
	/** Getters and Setters **/
	
	public InterfaceListener getInterface(){
		return this;
	}
	
	/** Getters and Setters End **/
}