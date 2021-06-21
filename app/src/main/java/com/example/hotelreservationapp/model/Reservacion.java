package com.example.hotelreservationapp.model;

public class Reservacion {


    String reservacionId;
    String clientName;
    String bedroomName;
    String bedroomNumber;
    String price;

    public Reservacion(String reservacionId, String clientName, String bedroomName, String bedroomNumber, String price) {
        this.reservacionId = reservacionId;
        this.clientName = clientName;
        this.bedroomName = bedroomName;
        this.bedroomNumber = bedroomNumber;
        this.price = price;
    }

    public String getReservacionId() {
        return reservacionId;
    }

    public void setReservacionId(String reservacionId) {
        this.reservacionId = reservacionId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
}
