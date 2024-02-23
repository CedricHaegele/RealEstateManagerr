package com.example.realestatemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.repository.RealEstateRepository;

public class AddRealtyViewModel extends AndroidViewModel {

    private final RealEstateRepository repository;

    public AddRealtyViewModel(@NonNull Application application) {
        super(application);
        repository = new RealEstateRepository(application.getApplicationContext());
    }

    public LiveData<Long> addProperty(RealEstate realEstate) {
        return repository.addRealState(realEstate);
    }
}
