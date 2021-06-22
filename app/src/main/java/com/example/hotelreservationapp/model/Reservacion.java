package com.example.hotelreservationapp.model;

public class Reservacion {

    String reservacionId;
    String clientName;
    String signInHour;
    String signtOutHour;
    String Guestsquantity;
    String bedroomName;

    public Reservacion(String reservacionId, String clientName, String signInHour, String signtOutHour,
                       String guestsquantity, String bedroomName) {
        this.reservacionId = reservacionId;
        this.clientName = clientName;
        this.signInHour = signInHour;
        this.signtOutHour = signtOutHour;
        Guestsquantity = guestsquantity;
        this.bedroomName = bedroomName;
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

    public String getSignInHour() {
        return signInHour;
    }

    public void setSignInHour(String signInHour) {
        this.signInHour = signInHour;
    }

    public String getSigntOutHour() {
        return signtOutHour;
    }

    public void setSigntOutHour(String signtOutHour) {
        this.signtOutHour = signtOutHour;
    }

    public String getGuestsquantity() {
        return Guestsquantity;
    }

    public void setGuestsquantity(String guestsquantity) {
        Guestsquantity = guestsquantity;
    }

    public String getBedroomName() {
        return bedroomName;
    }

    public void setBedroomName(String bedroomName) {
        this.bedroomName = bedroomName;
    }
}
