package com.example.jetty_jersey.ws;

import com.example.jetty_jersey.classes.Plane;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/plane")
public class PlaneResource {

    public List<Plane> remplirBase(){
        List<Plane> liste = new ArrayList<Plane>();
        Plane p1 = new Plane("ACT123",5);
        Plane p2 = new Plane("ACT124",3);
        Plane p3 = new Plane("ACT125",8);
        Plane p4 = new Plane("ACT126",7);
        Plane p5 = new Plane("ACT127",2);
        liste.add(p1);
        liste.add(p2);
        liste.add(p3);
        liste.add(p4);
        liste.add(p5);
        return liste;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planes")
    public List<Plane> getAllPlane() {
        return remplirBase();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public String createPlane() {
        return "SUCCESS";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planes/{atcNumber}")
    public Plane getPlane(@PathParam("atcNumber") String id) {
        List<Plane> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getAtcNumber().equals(id)) return all.get(i);
        }
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/planes/{atcNumber}")
    public String updatePlane(@PathParam("atcNumber") String id){
        return "SUCCESS";
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/planes/{atcNumber}")
    public List<Plane> deletePlane(@PathParam("atcNumber") String id){
        List<Plane> all = remplirBase();
        for (int i=0; i<all.size();i++){
            if( all.get(i).getAtcNumber().equals(id)){
                all.remove(all.get(i));
                return all;
            }
        }
        return  all;
    }
}
