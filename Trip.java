package com.example.joeco.ecotaxiphoneapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Trip {

    private String email;
    private String carType;
    private LatLng pickup;
    private LatLng dest;
    private ArrayList<LatLng> MarkerPoints;


    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Trip(String email, String carType, ArrayList MarkerPoints){
        this.carType = carType;
        this.MarkerPoints = MarkerPoints;
        this.email = email;
        this.dest = dest;
        this.pickup = pickup;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LatLng getPickup() {
        return pickup;
    }

    public void setPickup(LatLng pickup) {
        this.pickup = pickup;
    }

    public LatLng getDest() {
        return dest;
    }

    public void setDest(LatLng dest) {
        this.dest = dest;
    }

    public ArrayList<LatLng> getMarkerPoints() {
        return MarkerPoints;
    }

    public void setMarkerPoints(ArrayList<LatLng> markerPoints) {
        MarkerPoints = markerPoints;
    }
}
