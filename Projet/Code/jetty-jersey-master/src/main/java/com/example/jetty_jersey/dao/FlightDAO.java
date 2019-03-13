package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Flight;

import java.util.Date;
import java.util.List;

public interface FlightDAO {
    boolean createFLight( Flight plane );
    boolean updateFLight(Flight plane);
    boolean deleteFlight(String planeId);
    List<Flight> searchFlight(String departure_aerodrome, Date date);
    List<Flight> getListeFlight();
    Flight getFlightDetails(String idFlight);
}
