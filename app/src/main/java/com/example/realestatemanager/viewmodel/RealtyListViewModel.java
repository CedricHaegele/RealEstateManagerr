package com.example.realestatemanager.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanager.model.RealtyList;
import com.example.realestatemanager.repository.RealtyRepository;

import java.util.List;

public class RealtyListViewModel extends AndroidViewModel {
    private LiveData<List<RealtyList>> realtyLists;
    private RealtyRepository repository;

    public RealtyListViewModel(@NonNull Application application) {
        super(application);
        repository = new RealtyRepository(application.getApplicationContext());
        realtyLists = repository.getAllRealties();
    }

    public LiveData<List<RealtyList>> getRealtyLists() {
        return realtyLists;
    }

}
