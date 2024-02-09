package com.example.realestatemanager.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MapViewModel extends AndroidViewModel {
    private MutableLiveData<Boolean> permissionGranted = new MutableLiveData<>();

    public MapViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getPermissionGranted() {
        return permissionGranted;
    }

    public void updatePermissionStatus(boolean isGranted) {
        permissionGranted.setValue(isGranted);
    }
}

