package com.example.realestatemanager.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.realestatemanager.entities.Realty;
import com.example.realestatemanager.repository.RealtyRepository;

import java.util.List;

public class RealtyViewModel extends AndroidViewModel {

    private RealtyRepository repository;
    private LiveData<List<Realty>> allRealties;

    public RealtyViewModel(Application application) {
        super(application);
        repository = new RealtyRepository(application);
        allRealties = repository.getAllRealties();
    }

    public LiveData<List<Realty>> getAllRealties() {
        return allRealties;
    }

    public void insert(Realty realty) {
        repository.insert(realty);
    }

    public void update(Realty realty) {
        repository.update(realty);
    }

    public void delete(Realty realty) {
        repository.delete(realty);
    }
}

