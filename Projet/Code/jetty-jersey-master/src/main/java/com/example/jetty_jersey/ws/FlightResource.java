package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonFlightDAO;
import com.example.jetty_jersey.classes.Flight;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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
    public boolean createFlight(Flight fl) {
        return bfdao.createFLight(fl);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights/{flightId}")
    public Flight getFlight(@PathParam("flightId") String flightId) {
        return  bfdao.getFlightDetails(flightId);
    }
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/flights/{flightId}")
    public boolean updateFlight(@PathParam("flightId") String flightId){
        return bfdao.updateFLight(flightId);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights/{flightId}")
    public boolean deleteFlight(@PathParam("flightId") String flightId){
        return  bfdao.deleteFlight(flightId);
    }
}
