package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Reservation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("reservation")
public class ReservationResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations")
    public List<Reservation> getAllReservation() {
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createReservation() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}")
    public Reservation getReservationById(@PathParam("reservationId") String reservationId) {

        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/user/{userId}")
    public Reservation getReservationByUser(@PathParam("userId") String userId) {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/Flight/{flightId}")
    public List<Reservation> getReservationByFlight(@PathParam("flightId") String flightId) {
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}")
    public String updateReservation(@PathParam("reservationId") String reservationId){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{reservationId}")
    public List<Reservation> deleteReservation(@PathParam("reservationId") String reservationId){
        return null;
    }
}
