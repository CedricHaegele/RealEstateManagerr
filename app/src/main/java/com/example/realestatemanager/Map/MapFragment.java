package com.example.realestatemanager.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.realestatemanager.R;
import com.example.realestatemanager.Model.RealEstateProperty;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101;
    private GoogleMap mMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapViewModel mapViewModel = new ViewModelProvider(this).get(MapViewModel.class);
        mapViewModel.getProperties().observe(getViewLifecycleOwner(), this::updateMap);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng newYork = new LatLng(40.7128, -74.0060);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newYork, 10));

        enableMyLocationIfPermitted();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.addMarker(new MarkerOptions().position(newYork).title("New York"));
    }


    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Calling the super method
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocationIfPermitted();
            }
        }
    }

    private void updateMap(List<RealEstateProperty> properties) {
        if (mMap != null) {
            mMap.clear();
            for (RealEstateProperty property : properties) {
                LatLng location = new LatLng(property.getLatitude(), property.getLongitude());
                mMap.addMarker(new MarkerOptions().position(location).title(property.getName()));

            }
        }

    }
}