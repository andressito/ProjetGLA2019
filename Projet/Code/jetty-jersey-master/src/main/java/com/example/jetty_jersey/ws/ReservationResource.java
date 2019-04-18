package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonReservationDAO;
import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.classes.Reservation;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("reservation")
public class ReservationResource {
    public BouchonReservationDAO brDAO = new BouchonReservationDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations")
    public List<Reservation> getAllReservation() {
        return brDAO.getAllReservation();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createReservation(Reservation reservation) {
        return brDAO.createReservation(reservation);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}")
    public Reservation getReservationById(@PathParam("reservationId") String reservationId) {
    	 return  brDAO.getReservationDetails(reservationId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/user/{userId}")
    public ArrayList<Reservation> getReservationByUser(@PathParam("userId") String userId) {
    	return brDAO.getReservationByUserId(userId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/Flight/{flightId}")
    public ArrayList<Reservation> getReservationByFlight(@PathParam("flightId") String flightId) {
    	return brDAO.getReservationByFlight(flightId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}")
    public String updateReservation(@PathParam("reservationId") String reservationId){
        return "SUCCESS";
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}/{state}")
    public boolean updateReservationState(@PathParam("reservationId") String reservationId, @PathParam("state") String state){
        return brDAO.validateReservation(reservationId,state);
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}")
    public boolean deleteReservation(@PathParam("reservationId") String reservationId){
        return brDAO.deleteReservation(reservationId);
    }
}