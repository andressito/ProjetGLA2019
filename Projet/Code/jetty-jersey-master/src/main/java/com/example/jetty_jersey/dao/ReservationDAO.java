package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Reservation;

import java.util.ArrayList;
import java.util.List;

public interface ReservationDAO {
    boolean createReservation(Reservation reservation);
    boolean updateReservation( String reservationId);
    List<Reservation> getAllReservation();
    Reservation getReservationDetails(String reservationId);
    boolean deleteReservation(String reservationId);
    boolean validateReservation(String reservationId,String state);
    ArrayList<Reservation> getReservationByUserId(String userId);
    ArrayList<Reservation> getReservationByFlight(String flight);
}