package com.example.realestatemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.realestatemanager.Converters;
import com.example.realestatemanager.dao.PhotoDao;
import com.example.realestatemanager.dao.RealtyListDao;
import com.example.realestatemanager.model.Photo;
import com.example.realestatemanager.model.RealtyList;

@Database(entities = {RealtyList.class, Photo.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "realty_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract RealtyListDao realtyListDao();
    public abstract PhotoDao photoDao();
}
