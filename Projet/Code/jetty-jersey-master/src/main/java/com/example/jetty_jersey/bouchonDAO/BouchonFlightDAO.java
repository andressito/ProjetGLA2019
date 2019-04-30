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
    static ArrayList<Flight>  liste= new ArrayList<Flight>();
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
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getFlightId().equals(flightId)){
                //liste.set(i,plane);
                return true;
            }
        }
        return false;
    }
    public boolean updateFlightReservation(String flightId, String remainingPlaces) {
        try{
            //JettyMain.c.updateFlightRemainingPlaces(flightId,remainingPlaces);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
    public ArrayList<Flight> searchFlight(String departure_aerodrome, String date) {
        try {
            return JettyMain.c.getFlights(departure_aerodrome,null,date,null,null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Flight> getListeFlight() {
        try {
            liste = JettyMain.c.allFlight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }
    public Flight getFlightDetails(String idFlight) {
        try {
            return JettyMain.c.getFlightByFlightId(idFlight);
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
        /*DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date todayFormat=null;
        Date today = new Date(System.currentTimeMillis());
        String format = df.format(today);
        try {
            todayFormat= df.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<Flight> liste = getListeFlight();
        ArrayList<Flight> listeDeMesVols =null;
        for(int i=0; i<liste.size();i++){
            if(liste.get(i).getUserId().equals(userId))
                listeDeMesVols.add(liste.get(i));
        }
        System.out.println(listeDeMesVols.size());
        if(listeDeMesVols.size()==0)
            return null;
        else{
            return  listeDeMesVols.get(0);
            Flight res = null;
            for(int i=0; i<listeDeMesVols.size(); i++){
                try {
                    Date d=df.parse(listeDeMesVols.get(i).getDate());
                    if(res==null){
                        res=listeDeMesVols.get(i);

                    }else if((todayFormat.before(d) || todayFormat.equals(d)) &&  df.parse(res.getDate()).after(d)){
                        res=listeDeMesVols.get(i);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return res;
        }*/
        //return null;
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
