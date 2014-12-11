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


public class LocationPage extends Activity implements LocationListener{
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
			setContentView(R.layout.location);
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
			 
			TextView txtloc = (TextView) findViewById(R.id.latlong); //(R.id.product_label);

		        
			Intent intent = getIntent();
			String streetAddress = intent.getStringExtra("StreetName");
			Log.d("address string", " > "+ streetAddress);
			Geocoder geo = new Geocoder(this);
			try {
			address	= (Address) geo.getFromLocationName(streetAddress, 1).get(0);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LatLng addLtLn = new LatLng(address.getLatitude(),address.getLongitude());
			Log.d("Lat Lon :", " : "+ address.getLatitude() + " : " +address.getLongitude());
			
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

			TextView txtloc2 = (TextView) findViewById(R.id.latlong2); //(R.id.product_label);
			txtloc2.setText("Your Location: " + strAddress);
			txtloc.setText("Selected Location: " + streetAddress);
			try {
				// Loading map
				initilizeMap();

				// Changing map type
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

				// Showing / hiding your current location
				googleMap.setMyLocationEnabled(true);

				// Enable / Disable zooming controls
				googleMap.getUiSettings().setZoomControlsEnabled(false);

				// Enable / Disable my location button
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				// Enable / Disable Compass icon
				googleMap.getUiSettings().setCompassEnabled(true);

				// Enable / Disable Rotate gesture
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				// Enable / Disable zooming functionality
				googleMap.getUiSettings().setZoomGesturesEnabled(true);

				/*double latitude = 17.385044;
				double longitude = 78.486671;
				GPSTracker tracker = new GPSTracker(this);
			    if (tracker.canGetLocation() == false) {
			        tracker.showSettingsAlert();
			    } else {
			        latitude = tracker.getLatitude();
			        longitude = tracker.getLongitude();
			      */  
			        MarkerOptions marker = new MarkerOptions().position(
							yourLocation)
							.title("Your Current Location: " + strAddress);
			        marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			        


			    
				

				// lets place some 10 random markers
				/*for (int i = 0; i < 10; i++) {
					// random latitude and longitude
					double[] randomLocation = createRandLocation(latitude,
							longitude);*/

					// Adding a marker
					MarkerOptions marker2 = new MarkerOptions().position(addLtLn)
							.title(" "+ streetAddress);
					marker2.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					
			        googleMap.addMarker(marker);
			        googleMap.addMarker(marker2);
			        
			        CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(yourLocation).zoom(15).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));
					/*Log.e("Random", "> " + randomLocation[0] + ", "
							+ randomLocation[1]);

					// changing marker color
					if (i == 0)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
					if (i == 1)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
					if (i == 2)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
					if (i == 3)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
					if (i == 4)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
					if (i == 5)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
					if (i == 6)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_RED));
					if (i == 7)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
					if (i == 8)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
					if (i == 9)
						marker.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

					googleMap.addMarker(marker);

					// Move the camera to last position with a zoom level
					if (i == 9) {
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(new LatLng(randomLocation[0],
										randomLocation[1])).zoom(15).build();

						googleMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
					}
				}*/

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
