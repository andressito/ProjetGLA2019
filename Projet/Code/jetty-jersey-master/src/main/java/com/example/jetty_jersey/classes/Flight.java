package com.example.jetty_jersey.classes;

import java.util.Date;

public class Flight {
    public String flightId;
    public String departureAerodrom;
    public String arrivalAerodrom;
    public Date date;
    public String atcNumber;
    public String userId;

    public Flight(String idFlight, String departureAerodrom, String arrivalAerodrom, Date date, String atcNumber, String userId){
        this.flightId= idFlight;
        this.departureAerodrom= departureAerodrom;
        this.arrivalAerodrom=arrivalAerodrom;
        this.date=date;
        this.atcNumber=atcNumber;
        this.userId=userId;
    }
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getDepartureAerodrom() {
        return departureAerodrom;
    }

    public void setDepartureAerodrom(String departureAerodrom) {
        this.departureAerodrom = departureAerodrom;
    }

    public String getArrivalAerodrom() {
        return arrivalAerodrom;
    }

    public void setArrivalAerodrom(String arrivalAerodrom) {
        this.arrivalAerodrom = arrivalAerodrom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAtcNumber() {
        return atcNumber;
    }

    public void setAtcNumber(String actNumber) {
        this.atcNumber = actNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
