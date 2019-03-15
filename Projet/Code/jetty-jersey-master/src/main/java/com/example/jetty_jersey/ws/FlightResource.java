package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonFlightDAO;
import com.example.jetty_jersey.classes.Flight;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/flight")
public class FlightResource {


    BouchonFlightDAO bfdao = new BouchonFlightDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights")
    public List<Flight> getAllFlight() {
        return bfdao.getListeFlight();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createFlight(@FormParam("plane") String plane,
                @FormParam("departureAero") String departureAero,
                @FormParam("date") Date date,
                @FormParam("departureTime") String departureTime,
                @FormParam("seats") String seats,
                @FormParam("type") String type,
                @FormParam("arrivalAedrome") String arrivalAedrome,
                @FormParam("arrivalTime") String arrivalTime,
                @FormParam("price") String price) {

            Flight fl = new Flight("1",departureAero,arrivalAedrome,date,plane,"2");
            bfdao.createFLight(fl);
            if(bfdao.createFLight(fl))return "OK";
            return "NONOK";
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
