package com.example.palsyeeg;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class FragmentGoogleMap extends Fragment implements OnMapReadyCallback {
    MapFragment mapFragment;

    public static MapFragment newInstance(String name) {
        MapFragment map = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", name);
        map.setArguments(bundle);
        return map;
    }

    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = mInflater.inflate(R.layout.activity_fragment_google_map, container, false);
        initInstance();
        return view;
    }

    private void initInstance() {
        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTraction = getFragmentManager().beginTransaction();
        fragmentTraction.add(R.id.mapFragment, mapFragment);
        fragmentTraction.commit();
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
