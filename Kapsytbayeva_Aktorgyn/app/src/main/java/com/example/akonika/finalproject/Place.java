package com.example.akonika.finalproject;

import com.google.android.gms.maps.model.LatLng;


public class Place {
    public int type;
    public LatLng point;
    Place( int type,LatLng point)
    {
        this.point = point;
        this.type = type;
    }
    public String toString()
    {
        return "mesto";
    }
}
