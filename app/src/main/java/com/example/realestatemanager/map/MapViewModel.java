package com.example.realestatemanager.map;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.realestatemanager.model.RealEstateProperty;

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
        // Créez une ArrayList spécifique de RealEstateProperty
        List<RealEstateProperty> loadedProperties = new ArrayList<RealEstateProperty>();

        // Ajoutez des objets RealEstateProperty à la liste
        loadedProperties.add(new RealEstateProperty(40.7128, -74.0060, "New York Property", "Description here", 1000000));
        // Ajoutez d'autres propriétés si nécessaire

        // Mettez à jour le LiveData avec la liste de RealEstateProperty
        properties.setValue(loadedProperties);
    }


    public LiveData<List<RealEstateProperty>> getProperties() {
        return properties;
    }
}
