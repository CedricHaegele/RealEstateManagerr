package com.example.realestatemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.realestatemanager.Converters;
import com.example.realestatemanager.dao.RealEstateDao;
import com.example.realestatemanager.model.RealEstate;

@Database(entities = {RealEstate.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static AppDatabase TEST_INSTANCE;

    public static synchronized AppDatabase getInstance(Context context) {

        if (TEST_INSTANCE != null) {
            return TEST_INSTANCE;
        }

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "realty_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract RealEstateDao realtyListDao();

    public static void setTestInstance(AppDatabase testInstance) {
        TEST_INSTANCE = testInstance;
    }
    public static AppDatabase createInMemoryDatabase(Context context) {
        return Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
}
