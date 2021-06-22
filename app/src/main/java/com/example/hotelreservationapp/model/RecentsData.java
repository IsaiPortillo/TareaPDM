package com.example.hotelreservationapp.model;
import android.app.Application;

import java.util.List;

public class RecentsData{
    String id;
    String bedroomName;
    String bedroomNumber;
    String price;
    Integer imageUrl;



    public RecentsData(String id, String bedroomName, String bedroomNumber, String price, Integer imageUrl) {
        this.id = id;
        this.bedroomName = bedroomName;
        this.bedroomNumber = bedroomNumber;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBedroomName() {
        return bedroomName;
    }

    public void setBedroomName(String bedroomName) {
        this.bedroomName = bedroomName;
    }

    public String getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(String bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

}
