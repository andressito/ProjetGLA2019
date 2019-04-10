package com.example.jetty_jersey.classes;

public class Reservation {
    public String reservationId;
    public String userId;
    public String flightId;
    public int nbPlaces;
    public String date;
    public int price;
    public String status;


    public Reservation(){}

    public Reservation(String reservationId,
                       String userId,
                       String flightId,
                       String date, int nbPlaces ,int price, String status){
        this.reservationId= reservationId;
        this.userId=userId;
        this.flightId = flightId;
        this.nbPlaces= nbPlaces;
        this.date=date;
        this.price=price;
        this.status=status;
    }

    public String getReservationId() {
        return reservationId;
    }
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public String getDate() {
        return date;
    }

    public int getPrice() {
        return price;
    }
    public String getStatus() {
        return status;
    }
    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(int prix) {
        this.price = prix;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}