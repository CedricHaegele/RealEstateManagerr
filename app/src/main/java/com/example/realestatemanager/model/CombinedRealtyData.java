package com.example.realestatemanager.model;

import java.util.List;

public class CombinedRealtyData {
    private RealtyList realtyList;
    private List<Photo> photos;

    public CombinedRealtyData(RealtyList realtyList, List<Photo> photos) {
        this.realtyList = realtyList;
        this.photos = photos;
    }

    public RealtyList getRealtyList() {
        return realtyList;
    }

    public void setRealtyList(RealtyList realtyList) {
        this.realtyList = realtyList;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
