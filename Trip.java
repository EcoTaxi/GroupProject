package com.example.joeco.ecotaxiphoneapp;

import com.google.android.gms.maps.model.LatLng;

public class Trip {

    private String email;
    private LatLng pickup;
    private LatLng dest;

    public Trip(String email, LatLng pickup, LatLng dest){
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
}
