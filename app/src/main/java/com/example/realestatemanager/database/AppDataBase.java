package com.example.realestatemanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.realestatemanager.DAO.RealtyDao;

import com.example.realestatemanager.entities.Realty;

// Spécifiez les entités et la version de la base de données
@Database(entities = {Realty.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    // Déclaration du DAO
    public abstract RealtyDao realtyDao();

    // Singleton instance
    private static volatile AppDataBase INSTANCE;

    // Méthode pour obtenir l'instance de la base de données
    public static AppDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    // Création de la base de données
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class, "real_estate_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
