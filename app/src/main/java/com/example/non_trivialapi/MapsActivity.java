package com.example.non_trivialapi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

        private GoogleMap mMap;
        private Double latitude, longitude;
        ArrayList<LatLng> markerPoints= new ArrayList<>();


        Button btnShowLocation;
        private static final int REQUEST_CODE_PERMISSION = 2;
        String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

        // GPSTracker class
        com.example.non_trivialapi.GPSTracker gps;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_maps);

                try {
                        if (ActivityCompat.checkSelfPermission(this, mPermission)
                        != getPackageManager().PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{mPermission}, REQUEST_CODE_PERMISSION);
                        // try permission aboveallowed or not by user
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

                btnShowLocation = (Button) findViewById(R.id.button);

                // show location button click event
                btnShowLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                        // create class object
                        gps = new com.example.non_trivialapi.GPSTracker(MapsActivity.this);

                        // check if GPS enabled
                        if (gps.canGetLocation()) {
                                        latitude = gps.getLatitude();
                                longitude = gps.getLongitude();

                                Toast.makeText(getApplicationContext(), "Location:\nLatitude: "+ latitude + "\nLongitude: " + longitude, Toast.LENGTH_LONG).show();
                                ShowMap();
                        } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                                gps.showSettingsAlert();
                        }
                }
                });
        }
        public void ShowMap() {
                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
        }

        /**
         * This callback is triggered when the map is ready to be used.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                                // Adding new item to the ArrayList
                                markerPoints.add(latLng);

                                // Creating MarkerOptions
                                MarkerOptions options = new MarkerOptions();

                                // Setting the position of the marker
                                options.position(latLng);

                                if (markerPoints.size() == 1)
                                {
                                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                        options.title("Point");
                                }
                                if (markerPoints.size() > 0) {
                                        markerPoints.clear();
                                }
                                // Add new marker to the Google Map Android API V2
                                mMap.addMarker(options);
                        }
                });
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);


                // Add a marker in current location and move the camera
                LatLng mylocation = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(mylocation).title("current location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15));
                //add a circle around mark
                Circle circle = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(latitude, longitude))
                        .radius(600)
                        .strokeColor(Color.RED)
                        .fillColor(Color.TRANSPARENT));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                }
                mMap.setMyLocationEnabled(true);

                }

}
