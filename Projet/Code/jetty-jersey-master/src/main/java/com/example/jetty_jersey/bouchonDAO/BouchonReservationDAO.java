package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.classes.Reservation;
import com.example.jetty_jersey.dao.ReservationDAO;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BouchonReservationDAO implements ReservationDAO {
    public boolean createReservation(Reservation reservation) {
        try {
            if(JettyMain.c.indexDB(reservation, null))
                return JettyMain.c.updateFlightRemainingPlaces(reservation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReservation(String reservationId) {
        return false;
    }

    public List<Reservation> getAllReservation() {
        try {
			return JettyMain.c.allReservation();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    public Reservation getReservationDetails(String reservationId) {
    	 try {
             ArrayList<Reservation> listeRes = JettyMain.c.allReservation();
             for(int i=0; i<listeRes.size(); i++){
                 if(listeRes.get(i).getReservationId().equals(reservationId))return listeRes.get(i);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
    }

    public boolean deleteReservation(String reservationId) {
    	try{
            JettyMain.c.delete(reservationId, "reservation");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateReservation(Reservation reservation) {
    	try{
    		if(reservation.getStatus().equals("Failed")) {
        		ArrayList<Flight> fl = JettyMain.c.allFlight();
        		for(int i=0; i<fl.size(); i++){
                    if(fl.get(i).getFlightId().equals(reservation.getFlightId())) {
                    	fl.get(i).setRemainingSeats(fl.get(i).getRemainingSeats() + reservation.getNbPlaces());
                    	JettyMain.c.updateFlightInIndex(fl);
                    }
                }
    		}
            return JettyMain.c.updateReservationValidation(reservation.getReservationId(),reservation.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	public ArrayList<Reservation> getReservationByUserId(String userId) {
		try {
            ArrayList<Reservation> res = JettyMain.c.getReservationByUser(userId);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

	public ArrayList<Reservation> getReservationByFlight(String flight) {
		try {
            ArrayList<Reservation> res = JettyMain.c.getReservationByFlight(flight);
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

	public ArrayList<Reservation> getReservationForPilot(String userId){
        ArrayList<Flight> listFlight;
        ArrayList<Reservation> listReservation= new ArrayList<Reservation>();
        try {
            listFlight=JettyMain.c.getFlightByUserId(userId);
            for(Flight flight : listFlight){
                SearchHit[] sh =JettyMain.c.getByFieldValue("reservation","flightId",flight.getFlightId());
                if(sh!=null){
                    for(int i=0; i<sh.length;i++){
                        Reservation r =JettyMain.c.createReservation(sh[i].getSourceAsMap());
                        if(r.getStatus().equals("waiting"))
                            listReservation.add(r);
                    }
                }
            }
            return listReservation;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}