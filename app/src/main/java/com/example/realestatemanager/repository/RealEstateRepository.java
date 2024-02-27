package com.example.realestatemanager.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.realestatemanager.dao.RealEstateDao;
import com.example.realestatemanager.database.AppDatabase;
import com.example.realestatemanager.model.RealEstate;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RealEstateRepository {

    private final RealEstateDao realEstateDao;
    private final Executor executor;

    public RealEstateRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        realEstateDao = db.realtyListDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<RealEstate>> getAll() {
        return realEstateDao.getAll();
    }

    public LiveData<Long> addRealState(RealEstate realEstate) {
        MutableLiveData<Long> resultId = new MutableLiveData<>();
        executor.execute(() -> {
            long id = realEstateDao.insert(realEstate);
            resultId.postValue(id);
        });
        return resultId;
    }

    public LiveData<RealEstate> getRealEstate(int id) {
        return realEstateDao.getRealEstate(id);
    }

    public void update(RealEstate realEstate) {
        executor.execute(() -> realEstateDao.update(realEstate));
    }

    public void deleteProperty(RealEstate realEstate) {
        executor.execute(() -> {
            realEstateDao.delete(realEstate);
        });

    }
}