package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.classes.User;
import com.example.jetty_jersey.dao.FlightDAO;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BouchonFlightDAO implements FlightDAO {
    public boolean createFLight(Flight flight) {
        try {
            JettyMain.c.indexDB(flight,null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateFLight(String flightId) {
        // a implémenter
        return false;
    }
    public boolean updateFlightReservation(String flightId, String remainingPlaces) {
        //a implémenter
        return false;
    }
    public boolean deleteFlight(String planeId) {
        //a implémenter
        return false;
    }
    public ArrayList<Flight> searchFlight(String departure_aerodrome, String date) {
        try {
            return JettyMain.c.getFlights(departure_aerodrome,null,date,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Flight> searchFlightFilter(String departure_aerodrome,String date,String nb_seats,String price,String type) {
        try {
            return JettyMain.c.getFlights(departure_aerodrome,null,date,type, price,nb_seats);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Flight> getListeFlight() {
        try {
            return JettyMain.c.allFlight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Flight getFlightDetails(String idFlight) {
        try {
            ArrayList<Flight> listeFlight = JettyMain.c.allFlight();
            for(int i=0; i<listeFlight.size(); i++){
                if(listeFlight.get(i).getFlightId().equals(idFlight))return listeFlight.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Flight> getFLightByUserId(String userId){
        try {
            return JettyMain.c.getFlightByUserId(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Flight getClosesFlight(String userId){
        try {
            return JettyMain.c.getClosestFlight(userId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public User getPilotByFlightId(String flightId) {
        try {
            SearchHit[] sh =JettyMain.c.getByFieldValue("flight","flightId",flightId);
            if(sh!=null){
                if(sh.length!=0){
                    Flight f = JettyMain.c.createFlight(sh[0].getSourceAsMap());
                    BouchonUserDAO bouchonUserDAO= new BouchonUserDAO();
                    return bouchonUserDAO.getUserDetails(f.getUserId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
