package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Reservation;
import com.example.jetty_jersey.dao.ReservationDAO;

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

    public boolean validateReservation(String reservationId,String state) {
    	try{
    		JettyMain.c.updateReservationValidation(reservationId,state);
    		return true;
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
}