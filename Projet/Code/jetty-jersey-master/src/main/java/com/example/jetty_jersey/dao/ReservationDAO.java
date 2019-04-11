package com.example.jetty_jersey.dao;

import com.example.jetty_jersey.classes.Reservation;

import java.util.List;

public interface ReservationDAO {
    boolean createReservation(Reservation reservation);
    boolean updateReservation( String reservationId);
    List<Reservation> getAllReservation();
    Reservation getReservationDetails(String reservationId);
    boolean deleteReservation(String reservationId);
    boolean validateReservation(int reservationID);
}