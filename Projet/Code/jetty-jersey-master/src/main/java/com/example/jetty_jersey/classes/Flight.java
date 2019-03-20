package com.example.jetty_jersey.classes;

import java.util.Date;

public class Flight {
    public String flightId;
    public String atcNumber;
    public String departureAerodrom;
    public String date;
    public String departureTime;
    public String seats;
    public String type;
    public String arrivalAerodrom;
    public String arrivalTime;
    public String price;
    public String userId;

    //
    public Flight(String atcNumber,String departureAerodrom,
                  String date,String departureTime,String seats,String type,
                  String arrivalAerodrom,String arrivalTime,String price,String userId) {
        this.flightId = null;
        this.atcNumber = atcNumber;
        this.departureAerodrom = departureAerodrom;
        this.date = date;
        this.departureTime = departureTime;
        this.seats = seats;
        this.type = type;
        this.arrivalAerodrom = arrivalAerodrom;
        this.arrivalTime = arrivalTime;
        this.price = price;
        this.userId = userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getAtcNumber() {
        return atcNumber;
    }

    public void setAtcNumber(String atcNumber) {
        this.atcNumber = atcNumber;
    }

    public String getDepartureAerodrom() {
        return departureAerodrom;
    }

    public void setDepartureAerodrom(String departureAerodrom) {
        this.departureAerodrom = departureAerodrom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArrivalAerodrom() {
        return arrivalAerodrom;
    }

    public void setArrivalAerodrom(String arrivalAerodrom) {
        this.arrivalAerodrom = arrivalAerodrom;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
