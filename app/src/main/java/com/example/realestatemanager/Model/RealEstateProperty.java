package com.example.realestatemanager.Model;

public class RealEstateProperty {
    private final double latitude;
    private final double longitude;
    private final String name;
    private final String description;
    private final double price;

    public RealEstateProperty(double latitude, double longitude, String name, String description, double price) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters et Setters
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
}

