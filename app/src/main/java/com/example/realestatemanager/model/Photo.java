package com.example.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos")
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int realtyId;
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // Getter pour id
    public int getId() {
        return id;
    }

    // Setter pour id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour realtyId
    public int getRealtyId() {
        return realtyId;
    }

    // Setter pour realtyId
    public void setRealtyId(int realtyId) {
        this.realtyId = realtyId;
    }

}
