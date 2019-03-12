package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Reservation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("reservation")
public class ReservationResource {

    public List<Reservation> remplirBase(){
        List<Reservation> liste = new ArrayList<Reservation>();
        Reservation r1= new Reservation("R123","ID125","ATC123",1,25.5,"WAITING");
        Reservation r2= new Reservation("R129","ID126","ATC123",2,15,"CANCEL");
        Reservation r3= new Reservation("R124","ID127","ATC123",1,10,"CANCEL");
        Reservation r4= new Reservation("R125","ID128","ATC124",3,18,"WAITING");
        Reservation r5= new Reservation("R126","ID129","ATC125",1,20,"WAITING");
        liste.add(r1);
        liste.add(r2);
        liste.add(r3);
        liste.add(r4);
        liste.add(r5);
        return liste;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations")
    public List<Reservation> getAllReservation() {
        return remplirBase();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createReservation() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{idReservation}")
    public Reservation getReservationById(@PathParam("idReservation") String id) {
        List<Reservation> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdReservation().equals(id)) return all.get(i);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/user/{idUser}")
    public Reservation getReservationByUser(@PathParam("idUser") String id) {
        List<Reservation> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdUser().equals(id)) return all.get(i);
        }
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/Flight/{idFlight}")
    public List<Reservation> getReservationByFlight(@PathParam("idFlight") String id) {
        List<Reservation> all = remplirBase();
        List<Reservation> res = new ArrayList<Reservation>();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdFlight().equals(id)) res.add(all.get(i)) ;
        }
        return res;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/reservations/{idReservation}")
    public String updateReservation(@PathParam("idReservation") String id){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reservations/{idReservation}")
    public List<Reservation> deleteReservation(@PathParam("idReservation") String id){
        List<Reservation> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getIdReservation().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }
        return  all;
    }
}
