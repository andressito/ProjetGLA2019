package com.example.jetty_jersey.classes;

import java.util.Date;

public class Aeorodrome {
    public String aerodromeId;
    public String town;
    public String airfieldName;
    public String location;

    public Aeorodrome() {}
	public Aeorodrome(String town, String airfieldName, String location) {
		this.aerodromeId = null;
		this.town = town;
		this.airfieldName = airfieldName;
		this.location = location;
	}


	public String getAerodromeId() {
		return aerodromeId;
	}

	public void setAerodromeId(String aerodromeId) {
		this.aerodromeId = aerodromeId;
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
