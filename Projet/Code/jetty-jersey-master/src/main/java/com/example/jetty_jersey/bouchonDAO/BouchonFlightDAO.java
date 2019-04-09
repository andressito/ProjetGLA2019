package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.dao.FlightDAO;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getFlightId().equals(idFlight))return liste.get(i);
        }
        return null;
    }

    public Flight getFLightByUserId(String userId){
        try {
            ArrayList<Flight> liste = JettyMain.c.allFlight();
            ArrayList<Flight> res= new ArrayList<Flight>();
            for(int i=0; i<liste.size();i++){
                if(liste.get(i).getUserId().equals(userId))
                    res.add(liste.get(i));
            }
            if(res.size()!=0) return res.get(0);
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
}
