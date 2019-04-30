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
            SearchHit[] sh =JettyMain.c.getByFieldValue("flight","flightId",reservation.getFlightId());
            if(sh!=null){
                if(sh.length!=0){
                    Flight f = JettyMain.c.createFlight(sh[0].getSourceAsMap());
                    if (f.getUserId().equals(reservation.getUserId())) {
                        return false;
                    }else{
                        if(JettyMain.c.indexDB(reservation, null))
                            return JettyMain.c.updateFlightRemainingPlaces(reservation);
                    }
                }
            }
        }catch (IOException ex) {
            ex.printStackTrace();
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
    		if(reservation.getStatus().equals("decline")) {
                SearchHit[] sh =JettyMain.c.getByFieldValue("reservation","reservationId",reservation.getReservationId());
    		    if(sh!=null){
    		        if(sh.length!=0){
    		            Reservation res = JettyMain.c.createReservation(sh[0].getSourceAsMap());
    		            SearchHit[] shFlight = JettyMain.c.getByFieldValue("flight","flightId",res.getFlightId());
                        if(shFlight!=null) {
                            if (shFlight.length != 0) {
                                Flight f = JettyMain.c.createFlight(shFlight[0].getSourceAsMap());
                                f.setRemainingSeats(f.getRemainingSeats()+res.getNbPlaces());
                                JettyMain.c.updateFlightInIndex(f);
                            }
                        }
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
            return JettyMain.c.getReservationByUser(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}

	public ArrayList<Reservation> getReservationByFlight(String flight) {
		try {
            return JettyMain.c.getReservationByFlight(flight);
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
