package com.example.akonika.finalproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;
import java.util.ArrayList;


public class SecondActivity  extends FragmentActivity {
    public  boolean isVisibleHospital=true, isVisibleCinema=true, isVisibleCancer=true;
    ArrayList<Marker> mesto1,mesto2,mesto3;
    BlankFragment bf;
    DBHelper dbHelper;
    SQLiteDatabase db;
    GoogleMap mMap;
    private UiSettings ui;
    private static final LatLng CenterGoroda = new LatLng(43.2371,76.93481);
    final String TAG = "myLogs";
    String data[] = { "one", "two", "three", "four" };
    public int mt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mesto1 = new ArrayList<Marker>();
        mesto2 = new ArrayList<Marker>();
        mesto3 = new ArrayList<Marker>();
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        setUpMapIfNeeded();
        bf = new BlankFragment();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mt = position;
                Log.d(TAG, "here " + mt);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        getPlaces("1");
        getPlaces("2");
        getPlaces("3");
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mymap))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                init();
            }
        }
    }

    private void init() {
        mMap.setMyLocationEnabled(true);
        ui = mMap.getUiSettings();
        ui.setZoomControlsEnabled(true);
        ui.setCompassEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CenterGoroda, 13));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.d(TAG, "This is a point " + marker.getPosition().latitude + " " + marker.getPosition().longitude);
                double marklat = marker.getPosition().latitude;
                double marklong = marker.getPosition().longitude;
                Double toBeTruncatedLat = new Double(marklat);
                Double truncatedDoubleLat=new BigDecimal(toBeTruncatedLat ).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

                Double toBeTruncatedLong = new Double(marklong);
                Double truncatedDoubleLong=new BigDecimal(toBeTruncatedLong ).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                String marklatText = Double.toString(truncatedDoubleLat);
                String marklongText = Double.toString(truncatedDoubleLong);
                Log.d(TAG,"here is hero " + marklatText +" "+marklongText);
                if (db.delete("mytable", "lat=? and long=?",new String[] {marklatText,marklongText}) > 0) {
                    Log.d(TAG, "--- we delteeeeeed ---");
                    if(mesto1.contains(marker))
                    {
                        Log.d(TAG, "--- we udallllllll1 ---");
                        mesto1.remove(marker);
                    }
                    if(mesto2.contains(marker))
                    {
                        Log.d(TAG, "--- we udallllllll2 ---");
                        mesto2.remove(marker);
                    }
                    if(mesto3.contains(marker))
                    {
                        Log.d(TAG, "--- we udalllllll3 ---");
                        mesto3.remove(marker);
                    }
                    marker.remove();

                }
                return true;
            }
        });
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(TAG, "onMapClick: " + latLng.latitude + "," + latLng.longitude + " " + mt);
                ContentValues cv = new ContentValues();

                Double toBeTruncatedLat = new Double(latLng.latitude);
                Double truncatedDoubleLat=new BigDecimal(toBeTruncatedLat ).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

                Double toBeTruncatedLong = new Double(latLng.longitude);
                Double truncatedDoubleLong=new BigDecimal(toBeTruncatedLong ).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();

                cv.put("lat", truncatedDoubleLat);
                cv.put("long", truncatedDoubleLong);
                cv.put("type", mt+1);
                if (db.insert("mytable", null, cv) != -1) {
                    if (mt == 0) {
                        Marker m = mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.cinema)));
                        m.setDraggable(true);
                        mesto1.add(m);
                    }
                    if (mt == 1) {
                        Marker m = mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.dish)));
                        m.setDraggable(true);
                        mesto2.add(m);
                    }
                    if (mt == 2){
                        Marker m = mMap.addMarker(new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tree)));
                        m.setDraggable(true);
                        mesto3.add(m);
                    }

                }


            }
        });
    }

    public void onClickFilter(View view) {
        mMap.setMyLocationEnabled(false);
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
        ft.add(R.id.frgcnt, bf).commit();
    }
    public void dohit()
    {
        mMap.setMyLocationEnabled(true);
    }
    public void delelteBf()
    {
        android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_out_left, R.anim.slide_out_left);
        ft.remove(bf).commit();
    }

    public void getPlaces(String type)
    {

        Log.d(TAG,"here goes "+type);
        Cursor c = db.query("mytable", null, "type=?", new String[] {type}, null, null, null);
        if (c.moveToFirst()) {

            int latColIndex = c.getColumnIndex("lat");
            int longColIndex = c.getColumnIndex("long");

            do {
                if(type == "1")
                {
                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getFloat(latColIndex),c.getFloat(longColIndex)))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.cinema)));
                    m.setDraggable(true);
                    mesto1.add(m);
                    Log.d(TAG, "here goes the places we geeet");
                }
                if(type == "2")
                {
                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getFloat(latColIndex),c.getFloat(longColIndex)))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.dish)));
                    m.setDraggable(true);
                    mesto2.add(m);
                    Log.d(TAG, "here goes the places we geeet");
                }
                if(type == "3")
                {
                    Marker m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(c.getFloat(latColIndex),c.getFloat(longColIndex)))
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.tree)));
                    m.setDraggable(true);
                    mesto3.add(m);
                    Log.d(TAG, "here goes the places we geeet");
                }

            } while (c.moveToNext());
        } else
            Log.d(TAG, "0 rows");
        c.close();
    }
    public void hidemesto(int i)
    {
        if(i == 1)
        {
            for(int j = 0; j < mesto1.size();j++)
            {
                mesto1.get(j).setVisible(false);
            }
        }
        if(i == 2)
        {
            for(int j = 0; j < mesto2.size();j++)
            {
                mesto2.get(j).setVisible(false);
            }
        }
        if(i == 3)
        {
            for(int j = 0; j < mesto3.size();j++)
            {
                mesto3.get(j).setVisible(false);
            }
        }
    }
    public void showmesto(int i)
    {
        if(i == 1)
        {
            for(int j = 0; j < mesto1.size();j++)
            {
                mesto1.get(j).setVisible(true);
            }
        }
        if(i == 2)
        {
            for(int j = 0; j < mesto2.size();j++)
            {
                mesto2.get(j).setVisible(true);
            }
        }
        if(i == 3)
        {
            for(int j = 0; j < mesto3.size();j++)
            {
                mesto3.get(j).setVisible(true);
            }
        }
    }
}
