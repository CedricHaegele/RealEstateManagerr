package com.example.realestatemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.repository.RealEstateRepository;

import java.util.concurrent.Executors;

public class AddRealtyViewModel extends AndroidViewModel {
    private final RealEstateRepository repository;

    public AddRealtyViewModel(@NonNull Application application) {
        super(application);
        repository = new RealEstateRepository(application.getApplicationContext());
    }

    public LiveData<Long> addProperty(RealEstate realEstate) {
        return repository.addRealState(realEstate);
    }

    public void updateRealEstate(RealEstate realEstate) {
        Executors.newSingleThreadExecutor().execute(() -> repository.update(realEstate));
    }

    public LiveData<RealEstate> getRealEstate(int id) {
        return repository.getRealEstate(id);
    }
}
