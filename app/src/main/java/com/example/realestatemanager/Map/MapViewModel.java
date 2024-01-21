package com.example.realestatemanager.Map;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.realestatemanager.Model.RealEstateProperty;

import java.util.ArrayList;
import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private final MutableLiveData<List<RealEstateProperty>> properties;

    public MapViewModel(Application application) {
        super(application);
        properties = new MutableLiveData<>();
        loadProperties();
    }

    private void loadProperties() {

        List<RealEstateProperty> loadedProperties = new ArrayList<>();

        loadedProperties.add(new RealEstateProperty(40.7128, -74.0060, "New York Property", "Description here", 1000000));

        properties.setValue(loadedProperties);
    }


    public LiveData<List<RealEstateProperty>> getProperties() {
        return properties;
    }
}
