package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface FlightDAO {
    boolean createFLight( Flight flight );
    boolean updateFLight(String flightId);
    boolean updateFlightReservation(String flightId,String remainingPlaces);
    boolean deleteFlight(String flightId);
    ArrayList<Flight> searchFlight(String departure_aerodrome, String date);
    List<Flight> getListeFlight();
    Flight getFlightDetails(String idFlight);
    ArrayList<Flight> getFLightByUserId(String userId);
}
