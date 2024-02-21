package com.example.realestatemanager.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.realestatemanager.model.Photo;

import java.util.List;

@Dao
public interface PhotoDao {
    @Insert
    long insert(Photo photo);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);

    @Query("SELECT * FROM photos WHERE realtyId = :propertyId")
    LiveData<List<Photo>> getPhotosByPropertyId(int propertyId);

}
