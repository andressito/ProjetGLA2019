package com.example.jetty_jersey.classes;

public class Plane {
    public String atcNumber;
    public int numberSeats;

    public Plane(String atcNumber, int numberSeats){
        this.atcNumber=atcNumber;
        this.numberSeats=numberSeats;
    }

    public String getAtcNumber() {
        return atcNumber;
    }

    public void setAtcNumber(String atcNumber) {
        this.atcNumber = atcNumber;
    }

    public int getNumberSeats() {
        return numberSeats;
    }

    public void setNumberSeats(int numberSeats) {
        this.numberSeats = numberSeats;
    }
}
