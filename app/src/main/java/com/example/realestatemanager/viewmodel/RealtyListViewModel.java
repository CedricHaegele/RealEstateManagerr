package com.example.realestatemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanager.model.RealEstate;
import com.example.realestatemanager.repository.RealEstateRepository;

import java.util.List;

public class RealtyListViewModel extends AndroidViewModel {
    private final RealEstateRepository repository;

    public RealtyListViewModel(@NonNull Application application) {
        super(application);
        repository = new RealEstateRepository(application.getApplicationContext());
    }

    public LiveData<List<RealEstate>> getRealtyLists() {
        return repository.getAll();
    }

    public LiveData<RealEstate> getRealEstate(int id) {
        return repository.getRealEstate(id);
    }
}
