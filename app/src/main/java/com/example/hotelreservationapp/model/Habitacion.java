package com.example.hotelreservationapp.model;
import android.app.Application;

import java.util.List;

public class Habitacion {
    String id;
    String bedroomName;
    String bedroomClass;
    int bedroomNumber;
    double price;
    String characteristics;
    int floorNumber;
    String availability;
    String imageUrl;

    public Habitacion(String id, String bedroomName, String bedroomClass, int bedroomNumber, double price,
                      String characteristics, int floorNumber, String availability, String imageUrl) {
        this.id = id;
        this.bedroomName = bedroomName;
        this.bedroomClass = bedroomClass;
        this.bedroomNumber = bedroomNumber;
        this.price = price;
        this.characteristics = characteristics;
        this.floorNumber = floorNumber;
        this.availability = availability;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBedroomName() {
        return bedroomName;
    }

    public void setBedroomName(String bedroomName) {
        this.bedroomName = bedroomName;
    }

    public String getBedroomClass() {
        return bedroomClass;
    }

    public void setBedroomClass(String bedroomClass) {
        this.bedroomClass = bedroomClass;
    }

    public int getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(int bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
