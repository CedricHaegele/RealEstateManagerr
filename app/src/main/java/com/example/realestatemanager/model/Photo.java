package com.example.realestatemanager.model;

public class Photo {

    private int id;

    private int realtyId;

    private String imageUri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRealtyId() {
        return realtyId;
    }

    public void setRealtyId(int realtyId) {
        this.realtyId = realtyId;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
