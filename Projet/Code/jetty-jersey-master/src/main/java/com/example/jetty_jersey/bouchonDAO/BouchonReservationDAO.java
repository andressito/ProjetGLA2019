package com.example.jetty_jersey.bouchonDAO;

import com.example.jetty_jersey.JettyMain;
import com.example.jetty_jersey.classes.Reservation;
import com.example.jetty_jersey.dao.ReservationDAO;

import java.util.ArrayList;
import java.util.List;

public class BouchonReservationDAO implements ReservationDAO {
    static List<Reservation>  liste= new ArrayList<Reservation>();
    public boolean createReservation(Reservation reservation) {
        try {
            liste.add(reservation);
            JettyMain.c.indexDB(reservation, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReservation(String reservationId) {
        return false;
    }

    public List<Reservation> getAllReservation() {
        return null;
    }

    public Reservation getReservationDetails(String reservationId) {
        return null;
    }

    public boolean deleteReservation(String reservationId) {
        return false;
    }

    public boolean validateReservation(int reservationID) {
        return false;
    }
}