package com.example.jetty_jersey.douchonDao;

import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.dao.FlightDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BouchonFlightDAO implements FlightDAO {
    static List<Flight>  liste= new ArrayList<Flight>();
    static{
        remplirBDFlight();
    }
    public static  void remplirBDFlight(){
        Flight f1 = new Flight("F123","DAKAR","DAKAR",new Date(),"AC123","ID125");
        Flight f2 = new Flight("F124","MEAUX","BOURGET",new Date(),"AC125","ID128");
        Flight f3 = new Flight("F125","BOURGET","BOURGET",new Date(),"AC127","ID127");
        Flight f4 = new Flight("F126","ST DENIS","ST DENIS",new Date(),"AC123","ID125");
        Flight f5 = new Flight("F127","DIDEROT","DIDEROT",new Date(),"AC124","ID126");
        liste.add(f1);
        liste.add(f2);
        liste.add(f3);
        liste.add(f4);
        liste.add(f5);
    }
    public boolean createFLight(Flight plane) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).atcNumber.equals(plane.atcNumber) &&
                    liste.get(i).getDate().equals(plane.getDate()) &&
                    liste.get(i).getDepartureAerodrom().equals(plane.getDepartureAerodrom())) return false;
        }
        liste.add(plane);
        return true;
    }
    public List<Flight> updateFLight(Flight plane) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getIdFlight().equals(plane.getIdFlight())){
                liste.set(i,plane);
            }
        }
        return liste;
    }
    public List<Flight> deleteFlight(String planeId) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getIdFlight().equals(planeId)){
                liste.remove(i);
                return liste;
            }
        }
        return liste;
    }
    public List<Flight> searchFlight(String departure_aerodrome, Date date) {
        return null;
    }

    public List<Flight> getListeFlight() {
        return liste;
    }
    public Flight getFlightDetails(String idFlight) {
        for(int i=0; i<liste.size(); i++){
            if(liste.get(i).getIdFlight().equals(idFlight))return liste.get(i);
        }
    }
}
