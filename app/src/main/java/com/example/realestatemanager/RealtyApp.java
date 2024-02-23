package com.example.realestatemanager;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

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
        createChannel();
    }

    public static RealtyApp getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MonCanal";
            String description = "Description du canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("canalId", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
