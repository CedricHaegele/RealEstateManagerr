package com.example.realestatemanager.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import com.example.realestatemanager.R;
import com.example.realestatemanager.fragments.DetailFragment;
import com.example.realestatemanager.model.AddressLoc;
import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.viewmodel.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 101; // Request code for location permission
    private GoogleMap mMap; // Google Map object
    private MapViewModel viewModel; // ViewModel for map related data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); // Set the layout for this activity

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);

        // Observers for location permission and realty list data
        viewModel.getLocationPermissionGranted().observe(this, this::updateMapUI);
        viewModel.getRealtyList().observe(this, this::addPropertyMarkers);

        // Initialize the map fragment dynamically and request map to be ready
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        initToolBar(); // Initialize the toolbar settings
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap; // Assign the map object
        setupMap(); // Setup map settings

        // Set initial position to New York and zoom
        LatLng newYork = new LatLng(40.7128, -74.0060);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newYork, 10));
    }

    private void setupMap() {
        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        // Enable location features on the map
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Set listener for marker clicks to open detail fragment
        mMap.setOnMarkerClickListener(marker -> {
            Integer propertyId = (Integer) marker.getTag(); // Get the property ID from the marker tag
            if (propertyId != null) {
                // If property ID is valid, navigate to the DetailFragment
                DetailFragment detailFragment = DetailFragment.newInstance(propertyId);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null) // Add transaction to back stack for proper navigation
                        .commit();
            } else {
                Log.e("MapActivity", "Property ID is null.");
            }

            return true; // Return true to indicate that we have handled the event
        });
    }

    private void updateMapUI(Boolean isGranted) {
        // Update map UI based on location permission status
        if (isGranted != null && isGranted && mMap != null) {
            setupMap(); // Setup map if permission is granted
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void addPropertyMarkers(List<RealEstate> properties) {
        // Add markers to the map for each property
        if (mMap == null) return; // Return if map is not initialized
        mMap.clear(); // Clear existing markers

        // Loop through properties and add markers
        for (RealEstate property : properties) {
            AddressLoc addressLoc = property.getAddressLoc();
            if (addressLoc != null && addressLoc.getLatLng() != null) {
                LatLng location = addressLoc.getLatLng();
                Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(property.getTitle()));
                assert marker != null;
                marker.setTag(property.getId()); // Set property ID as tag for marker
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Handle the result of location permission request
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            boolean isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            viewModel.updateLocationPermissionStatus(isGranted); // Update ViewModel with permission status
        }
    }

    private void initToolBar() {
        // Initialize toolbar settings
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable "Up" button
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(" Map"); // Set toolbar title
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle "Up" button click in toolbar
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
