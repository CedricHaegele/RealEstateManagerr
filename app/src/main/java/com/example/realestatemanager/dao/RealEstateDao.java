package com.example.realestatemanager.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

}
