package com.example.realestatemanager;

import android.app.Application;

import androidx.room.Room;

import com.example.realestatemanager.database.AppDatabase;

public class RealtyApp extends Application {

    private static RealtyApp instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "realty_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static RealtyApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
