package com.example.realestatemanager.model;

import android.content.ContentValues;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.w3c.dom.NameList;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "RealEstate")
public class RealEstate {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String price;
    private String surface;
    private String rooms;
    private String bedrooms;
    private String bathrooms;
    private AddressLoc addressLoc;
    private String agent;
    private String availableDate;
    private String soldDate;
    private List<String> imageUrls = new ArrayList<>();
    private List<String> pointsOfInterest = new ArrayList<>();

    public RealEstate() {
    }

    public RealEstate(int id, String title, String description, String price, String surface, String rooms, String bedrooms, String bathrooms, AddressLoc addressLoc, String agent, String availableDate, String soldDate, List<String> imageUrls) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.surface = surface;
        this.rooms = rooms;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.addressLoc = addressLoc;
        this.agent = agent;
        this.availableDate = availableDate;
        this.soldDate = soldDate;
        this.imageUrls = imageUrls;
    }

    public static RealEstate fromContentValues(ContentValues values) {
        RealEstate realEstate = new RealEstate();
        if (values.containsKey("id")) {
            realEstate.setId(values.getAsInteger("id"));
        }

        return realEstate;
    }

    public List<String> getPointsOfInterest() {
        return pointsOfInterest;
    }

    public void setPointsOfInterest(List<String> pointsOfInterest) {
        this.pointsOfInterest = pointsOfInterest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
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

    public AddressLoc getAddressLoc() {
        return addressLoc;
    }

    public void setAddressLoc(AddressLoc addressLoc) {
        this.addressLoc = addressLoc;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public boolean hasSchoolNearby() {
        return this.pointsOfInterest.contains("School");
    }

    public boolean hasShoppingNearby() {
        return this.pointsOfInterest.contains("Shopping");
    }

    public boolean hasTransportNearby() {
        return this.pointsOfInterest.contains("Transport");
    }

    public boolean hasPoolNearby() {
        return this.pointsOfInterest.contains("Swimming Pool");
    }

    public void addPointOfInterest(String pointOfInterest) {
        if (!this.pointsOfInterest.contains(pointOfInterest)) {
            this.pointsOfInterest.add(pointOfInterest);
        }
    }

    public void removePointOfInterest(String pointOfInterest) {
        this.pointsOfInterest.remove(pointOfInterest);
    }



    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                '}';
    }
}
