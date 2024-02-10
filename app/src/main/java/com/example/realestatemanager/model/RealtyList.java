package com.example.realestatemanager.model;

public class RealtyList {

    String title;
    String description;

    public RealtyList(String title, String description) {
        this.title = title;
        this.description = description;
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

    @Override
    public String toString() {
        return "RealtyList{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
