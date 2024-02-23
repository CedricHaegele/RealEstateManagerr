package com.example.realestatemanager.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "realty_list")
public class RealtyList {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String title;
    private String description;
    private String price;
    private String surface;
    private String rooms;
    private String bedrooms;
    private String bathrooms;
    private String address;
    private String availableDate;
    private String soldDate;
    private String imageUrl;
    private String imageBase64;

    private List<String> imageUrls = new ArrayList<>();

    public RealtyList(String title, String price, String address, String imageUrl, String imageBase64) {
        this.title = title;
        this.price = price;
        this.address = address;
        this.imageUrl = imageUrl;
        this.imageBase64 = imageBase64;
    }

    public RealtyList() {

    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getSurface() {
        return surface;
    }

    public String getRooms() {
        return rooms;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return "$ " + price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "RealtyList{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public String getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(String bathrooms) {
        this.bathrooms = bathrooms;
    }
}
