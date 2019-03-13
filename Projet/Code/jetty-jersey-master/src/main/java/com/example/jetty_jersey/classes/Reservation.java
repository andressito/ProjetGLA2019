package com.example.jetty_jersey.classes;

import java.util.Date;

public class Reservation {
    public String idReservation;
    public String idUser;
    public String idFlight;
    public int nbPlaces;
    public Date date;
    public double price;
    public String status;

    public Reservation(String idReservation, String idUser, String idFlight, int nbPlaces, double price, String status){
        this.idReservation= idReservation;
        this.idUser=idUser;
        this.idFlight = idFlight;
        this.nbPlaces= nbPlaces;
        this.price=price;
        this.status=status;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getIdFlight() {
        return idFlight;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setIdFlight(String idFlight) {
        this.idFlight = idFlight;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double prix) {
        this.price = prix;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
