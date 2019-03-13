package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Flight;

import java.util.Date;
import java.util.List;

public interface FlightDAO {
    boolean createFLight( Flight flight );
    List<Flight> updateFLight(Flight flight);
    List<Flight> deleteFlight(String planeId);
    List<Flight> searchFlight(String departure_aerodrome, Date date);
    List<Flight> getListeFlight();
    Flight getFlightDetails(String idFlight);
}
