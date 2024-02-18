package com.example.realestatemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.realestatemanager.model.RealtyList;

import java.util.List;

@Dao
public interface RealtyListDao {
    @Insert
    void insert(RealtyList realtyList);

    @Update
    void update(RealtyList realtyList);

    @Delete
    void delete(RealtyList realtyList);

    @Query("SELECT * FROM realty_list")
    LiveData<List<RealtyList>> getAll();

}

