package com.example.joeco.ecotaxiphoneapp;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Trip {

    private String email;
    private String carType;
    private LatLng pickup;
    private LatLng dest;
    private ArrayList<LatLng> MarkerPoints;
    private ArrayList<LatLng> StartStopPoints;



    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Trip(String email, String carType, ArrayList MarkerPoints,ArrayList StartStopPoints){
        this.carType = carType;
        this.MarkerPoints = MarkerPoints;
        this.email = email;
        this.StartStopPoints = StartStopPoints;
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

    public ArrayList<LatLng> getStartStopPoints() {
        return StartStopPoints;
    }

    public void setStartStopPoints(ArrayList<LatLng> startStopPoints) {
        StartStopPoints = startStopPoints;
    }
}
