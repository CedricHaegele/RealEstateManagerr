package com.example.realestatemanager.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.realestatemanager.entities.Realty;

import java.util.List;

@Dao
public interface RealtyDao {

    // Insérer un bien immobilier
    @Insert
    long insert(Realty realty);

    // Mettre à jour un bien immobilier
    @Update
    void update(Realty realty);

    // Supprimer un bien immobilier
    @Delete
    void delete(Realty realty);

    // Obtenir tous les biens immobiliers
    @Query("SELECT * FROM realty_table")
    LiveData<List<Realty>> getAllRealties();

    // Obtenir un bien immobilier par son ID
    @Query("SELECT * FROM realty_table WHERE id = :id")
    LiveData<Realty> getRealtyById(int id);

    // Autres requêtes comme nécessaire...
}

