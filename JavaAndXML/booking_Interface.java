package com.example.joeco.ecotaxiphoneapp;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class booking_Interface extends FragmentActivity implements OnMapReadyCallback,LocationListener
        {
    private GoogleMap mMap;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_booking__interface);



                SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                        getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

                    @Override
                    public void onPlaceSelected(Place place) {
                        Log.d("Maps", "Place selected: " + place.getName());


                    }

                    @Override
                    public void onError(Status status) {
                        Log.d("Maps", "An error occurred: " + status);
                    }


                });

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map2);
                mapFragment.getMapAsync(this);
            }



            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;




    }


            @Override
            public void onLocationChanged(Location location) {

            }
        }
