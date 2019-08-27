package com.project.android.zaloapp.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.android.zaloapp.R;

public class LocationMap extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    MapView mapView;
    View mView;
    public LocationMap(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanseState){
        mView = inflater.inflate(R.layout.map_fragment, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle saveInstanseState){
        super.onViewCreated(view, saveInstanseState);

        mapView = (MapView)mView.findViewById(R.id.map);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;
        LatLng myhome = new LatLng(21.029727, 105.800403);
        map.addMarker(new MarkerOptions().position(myhome).title("My Home"));
        map.moveCamera(CameraUpdateFactory.newLatLng(myhome));
    }
}


