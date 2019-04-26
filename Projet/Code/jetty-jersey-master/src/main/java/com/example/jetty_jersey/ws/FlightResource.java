package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonFlightDAO;
import com.example.jetty_jersey.classes.Flight;
import com.example.jetty_jersey.classes.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/flight")
public class FlightResource {
    BouchonFlightDAO bfdao = new BouchonFlightDAO();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights")
    public ArrayList<Flight> getAllFlight() {
        return bfdao.getListeFlight();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createFlight(Flight flight) {
        return bfdao.createFLight(flight);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{departure}/{date}")
    public ArrayList<Flight> researchFlight(@PathParam("departure") String departure,@PathParam("date") String date ) {
        if(date.equals("0")) return bfdao.searchFlight(departure,null);
        return bfdao.searchFlight(departure,date);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flightByUserId/{userId}")
    public ArrayList<Flight> getFlightByUserId(@PathParam("userId") String userId){
        return bfdao.getFLightByUserId(userId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights/{flightId}")
    public Flight getFlight(@PathParam("flightId") String flightId) {
        return  bfdao.getFlightDetails(flightId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/pilotByFlightId/{flightId}")
    public User getPilotByFlightId(@PathParam("flightId") String flightId) {
        return  bfdao.getPilotByFlightId(flightId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/flights/{flightId}")
    public boolean updateFlight(@PathParam("flightId") String flightId){
        return bfdao.updateFLight(flightId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/flights/{remainingSeats}/{flightId}")
    public boolean updateFlightReservation(@PathParam("flightId") String flightId,@PathParam("remainingSeats") String remainingSeats ){

        return bfdao.updateFlightReservation(flightId,remainingSeats);
    }
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights/{flightId}")
    public boolean deleteFlight(@PathParam("flightId") String flightId)
    {
        return  bfdao.deleteFlight(flightId);
    }
}
