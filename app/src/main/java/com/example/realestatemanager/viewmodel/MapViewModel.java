package com.example.realestatemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.repository.RealEstateRepository;

import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private final LiveData<Boolean> locationPermissionGranted;
    private final RealEstateRepository realEstateRepository;
    private LiveData<List<RealEstate>> realtyList;

    public MapViewModel(@NonNull Application application) {
        super(application);
        realEstateRepository = new RealEstateRepository(application);
        locationPermissionGranted = new MutableLiveData<>();
        loadRealtyList();
    }

    public LiveData<Boolean> getLocationPermissionGranted() {
        return locationPermissionGranted;
    }

    public void updateLocationPermissionStatus(boolean isGranted) {
        ((MutableLiveData<Boolean>) locationPermissionGranted).setValue(isGranted);
    }

    public LiveData<List<RealEstate>> getRealtyList() {
        return realtyList;
    }

    private void loadRealtyList() {
        realtyList = realEstateRepository.getAll();
    }
}
