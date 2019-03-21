package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.bouchonDAO.BouchonFlightDAO;
import com.example.jetty_jersey.classes.Flight;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public boolean createFlight(Flight fl) {
        return bfdao.createFLight(fl);
    }
    /*public String createFlight(@FormParam("plane") String plane,
                @FormParam("departureAero") String departureAero,
                @FormParam("date") String date,
                @FormParam("departureTime") String departureTime,
                @FormParam("seats") String seats,
                @FormParam("type") String type,
                @FormParam("arrivalAedrome") String arrivalAedrome,
                @FormParam("arrivalTime") String arrivalTime,
                @FormParam("price") String price) {

        String userId = "1";
        Flight fl = new Flight(plane,departureAero,date,departureTime,seats,type,arrivalAedrome,arrivalTime,price,userId);
        if(bfdao.createFLight(fl)) return "{\"success\" : \"new flight added\"} ";
        else return "{\"error\" : \"flight used\" }";
    }*/

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
