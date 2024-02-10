package com.example.realestatemanager.model;

public class RealtyList {

    private String title;
    private String description;
    private String price;
    private String surface;
    private String rooms;
    private String address;
    private String availableDate;
    private String soldDate;

    public RealtyList(String title, String price, String address) {
        this.title = title;
        this.price = price;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
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

    @Override
    public String toString() {
        return "RealtyList{" +
                "title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
