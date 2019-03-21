package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.dao.FlightDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BouchonFlightDAO implements FlightDAO {
    static List<Flight>  liste= new ArrayList<Flight>();
    public boolean createFLight(Flight flight) {
        try {

            JettyMain.c.indexDB(flight);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateFLight(String flightId) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getFlightId().equals(flightId)){
                //liste.set(i,plane);
                return true;
            }
        }
        return false;
    }
    public boolean deleteFlight(String planeId) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getFlightId().equals(planeId)){
                liste.remove(i);
                return true;
            }
        }
        return false;
    }
    public List<Flight> searchFlight(String departure_aerodrome, Date date) {
        return null;
    }

    public List<Flight> getListeFlight() {
        return liste;
    }

    public Flight getFlightDetails(String idFlight) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getFlightId().equals(idFlight))return liste.get(i);
        }
        return null;
    }
}
