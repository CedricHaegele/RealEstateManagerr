package com.example.realestatemanager.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "realty_table")
public class Realty {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String city;
    private double price;
    private String description;
    private double surface;
    private int bedrooms;
    private int bathrooms;
    private String address;
    private String agent;


    public Realty(String city, double price, String description, double surface, int bedrooms, int bathrooms, String address, String agent) {
        this.city = city;
        this.price = price;
        this.description = description;
        this.surface = surface;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.address = address;
        this.agent = agent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSurface() {
        return surface;
    }

    public void setSurface(double surface) {
        this.surface = surface;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }


    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}


