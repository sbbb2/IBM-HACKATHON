package com.example.hackathonsample;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ZipSpecificLocation extends Activity implements LocationListener{
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected Context context;
	TextView txtLat;
	String lat;
	String provider;
	GPSLocation gpsLoc;
	protected boolean gps_enabled,network_enabled;
	double latitude;
	double longitude;
	Address address ;
	String strAddress;
	List<Address> list;
	
	// Google Map
		private GoogleMap googleMap;
		
		@Override
		public void onLocationChanged(Location location) {
		
		 latitude = location.getLatitude();
		 longitude = location.getLongitude();
		}

		@Override
		public void onProviderDisabled(String provider) {
		Log.d("Latitude","disable");
		}

		@Override
		public void onProviderEnabled(String provider) {
		Log.d("Latitude","enable");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("Latitude","status");
		}
		

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.ziplocation);
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			 
				        
			Intent intent = getIntent();
			
			try {
				// Loading map
				initilizeMap();
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				googleMap.setMyLocationEnabled(true);
				googleMap.getUiSettings().setZoomControlsEnabled(false);
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);
				googleMap.getUiSettings().setCompassEnabled(true);
				googleMap.getUiSettings().setRotateGesturesEnabled(true);
				googleMap.getUiSettings().setZoomGesturesEnabled(true);
				Geocoder geo = new Geocoder(this);
				//String size = intent.getStringExtra("size");
				//int ab = Integer.parseInt(size);
				//TextView txtloc = (TextView) findViewById(R.id.latlong); //(R.id.product_label);
				//txtloc.setText("Your Location: " + size);
				for(int a = 0 ; a < 5 ;a++)
				{
					String streetAddress = intent.getStringExtra("StreetName"+a);
										
					try {
						address	= (Address) geo.getFromLocationName(streetAddress, 1).get(0);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						LatLng addLtLn = new LatLng(address.getLatitude(),address.getLongitude());
						
						MarkerOptions marker = new MarkerOptions().position(addLtLn)
								.title(" "+ streetAddress);
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
						
				        googleMap.addMarker(marker);	
					
						
				}		
			
			//Reverse GeoCode		
			
			gpsLoc = new GPSLocation(this);
			LatLng yourLocation = new LatLng(gpsLoc.getLatitude(),gpsLoc.getLongitude());
			
			if(Geocoder.isPresent()){
				  
				try {
					list = geo.getFromLocation(gpsLoc.getLatitude(),gpsLoc.getLongitude(),1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				  Address address = list.get(0);

				  StringBuffer str = new StringBuffer();
				  str.append("" + address.getAddressLine(1) + address.getPostalCode());

				  strAddress = str.toString();
				}

			     MarkerOptions marker = new MarkerOptions().position(
							yourLocation)
							.title("Your Current Location: " + strAddress);
			        marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			        

					
			        CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(yourLocation).zoom(15).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
					
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void onResume() {
			super.onResume();
			initilizeMap();
		}

		/**
		 * function to load map If map is not created it will create it for you
		 * */
		private void initilizeMap() {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}

		/*
		 * creating random position around a location for testing purpose only
		 */
		private double[] createRandLocation(double latitude, double longitude) {

			return new double[] { latitude + ((Math.random() - 0.5) / 500),
					longitude + ((Math.random() - 0.5) / 500),
					150 + ((Math.random() - 0.5) * 10) };
		}


}
