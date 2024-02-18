package com.example.realestatemanager.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.realestatemanager.dao.RealtyListDao;
import com.example.realestatemanager.database.AppDatabase;
import com.example.realestatemanager.model.RealtyList;

import java.util.List;

public class RealtyRepository {
    private RealtyListDao realtyListDao;
    private LiveData<List<RealtyList>> allRealties;

    public RealtyRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        realtyListDao = db.realtyListDao();
        allRealties = realtyListDao.getAll();
    }

    public LiveData<List<RealtyList>> getAllRealties() {
        return allRealties;
    }
}
