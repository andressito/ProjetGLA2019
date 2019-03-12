package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Flight;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/flight")
public class FlightResource {

    public List<Flight> remplirListeFlight(){
        List<Flight> liste = new ArrayList<Flight>();
        Flight f1 = new Flight("F123","DAKAR","DAKAR",new Date(),"AC123","ID125");
        Flight f2 = new Flight("F124","MEAUX","BOURGET",new Date(),"AC125","ID128");
        Flight f3 = new Flight("F125","BOURGET","BOURGET",new Date(),"AC127","ID127");
        Flight f4 = new Flight("F126","ST DENIS","ST DENIS",new Date(),"AC123","ID125");
        Flight f5 = new Flight("F127","DIDEROT","DIDEROT",new Date(),"AC124","ID126");
        liste.add(f1);
        liste.add(f2);
        liste.add(f3);
        liste.add(f4);
        liste.add(f5);
        return liste;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights")
    public List<Flight> getAllFlight() {
        return remplirListeFlight();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createFlight() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights/{idFlight}")
    public Flight getFlight(@PathParam("idFlight") String id) {
        List<Flight> all = remplirListeFlight();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdFlight().equals(id)) return all.get(i);
        }
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/flights/{idFlight}")
    public String updateFlight(@PathParam("idFlight") String id){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/flights/{idFlight}")
    public List<Flight> deleteFlight(@PathParam("idFlight") String id){
        List<Flight> all = remplirListeFlight();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdFlight().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }
        return  all;
    }
}
