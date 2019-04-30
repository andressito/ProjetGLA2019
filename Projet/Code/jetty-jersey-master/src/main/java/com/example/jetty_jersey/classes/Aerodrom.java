package com.example.jetty_jersey.classes;

public class Aerodrom {
    public String aerodromId;
    public String town;
    public String airfieldName;
    public String location;

    public Aerodrom() {}
    public Aerodrom(String town, String airfieldName, String location) {
        this.aerodromId = null;
        this.town = town;
        this.airfieldName = airfieldName;
        this.location = location;
    }


    public String getAerodromId() {
        return aerodromId;
    }

    public void setAerodromId(String aerodromeId) {
        this.aerodromId = aerodromeId;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAirfieldName() {
        return airfieldName;
    }

    public void setAirfieldName(String airfieldName) {
        this.airfieldName = airfieldName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
