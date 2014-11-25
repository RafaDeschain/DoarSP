package com.app.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public class LocationService{
	
	protected LocationManager locationManager;
	protected double latitude,longitude;
	protected Context context;
	
	// GPS status
    boolean isGPSEnabled = false;
 
    // Internet status
    boolean isNetworkEnabled = false;
	
	public LocationService(Context context){
		this.context = context;
		locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		
		//Verifica o status do GPS
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        //Verifica o status do 3G
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
        if(isGPSEnabled){
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        }
        else if(isNetworkEnabled){
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, listener);
        }
        
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		updateLocation(location);
	}
	
	private LocationListener listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			if(location != null){
				updateLocation(location);
			}
		}
	};

	private void updateLocation(Location location) {
		if(location != null){
			setLatitude(location.getLatitude());
			setLongitude(location.getLongitude());
		}
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
