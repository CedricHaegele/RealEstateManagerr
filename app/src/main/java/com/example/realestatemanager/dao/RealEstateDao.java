package com.example.realestatemanager.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.realestatemanager.model.RealEstate;

import java.util.List;

@Dao
public interface RealEstateDao {

    @Query("SELECT * FROM RealEstate")
    LiveData<List<RealEstate>> getAll();

    @Query("SELECT * FROM RealEstate WHERE id = :id")
    LiveData<RealEstate> getRealEstate(int id);

    @Query("SELECT * FROM RealEstate")
    Cursor getItemsWithCursor();

    @Insert
    long insert(RealEstate realEstate);

    @Update
    int update(RealEstate realEstate);

    @Query("DELETE FROM RealEstate WHERE id = :id")
    int deleteById(long id);

    @Query("SELECT * FROM RealEstate WHERE (:minSurface IS NULL OR surface >= :minSurface) AND (:maxSurface IS NULL OR surface <= :maxSurface) AND (:minPrice IS NULL OR price >= :minPrice) AND (:maxPrice IS NULL OR price <= :maxPrice) AND (:minRooms IS NULL OR rooms >= :minRooms) AND (:maxRooms IS NULL OR rooms <= :maxRooms) AND (:startDate IS NULL OR marketDate >= :startDate) AND (:endDate IS NULL OR marketDate <= :endDate)")
    LiveData<List<RealEstate>> searchProperties(Integer minSurface, Integer maxSurface, Integer minPrice, Integer maxPrice, Integer minRooms, Integer maxRooms, Long startDate, Long endDate);

}


